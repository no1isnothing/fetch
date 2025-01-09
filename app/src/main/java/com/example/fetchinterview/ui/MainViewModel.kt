package com.example.fetchinterview.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.asLiveData
import com.example.fetchinterview.data.MainRepository
import com.example.fetchinterview.data.NameItem
import com.example.fetchinterview.data.ViewItemBase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * MainViewModel for the project.
 *
 * Handles connection between [MainRepository] and [Fragment]s
 */
@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {
    val connectionStateData = mainRepository.connectionStateFlow.asLiveData()
    var _names = MutableLiveData<List<ViewItemBase>>()

    fun refreshNames() {
        viewModelScope.launch {
           _names.postValue(mainRepository.getNames())
        }
    }

    fun getNames() : LiveData<List<ViewItemBase>> {
        return _names
    }
}
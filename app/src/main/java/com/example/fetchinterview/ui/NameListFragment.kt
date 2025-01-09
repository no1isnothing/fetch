package com.example.fetchinterview.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.fetchinterview.data.ConnectionState
import com.example.fetchinterview.data.NameItem
import com.example.fetchinterview.data.ViewItemBase
import com.example.fetchinterview.databinding.FragmentNameListBinding
import com.google.common.flogger.FluentLogger
import dagger.hilt.android.AndroidEntryPoint

/**
 * A fragment representing a list of [NameItem]s.
 */
@AndroidEntryPoint
class NameListFragment : Fragment() {
    private var _binding: FragmentNameListBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by activityViewModels()
    private val nameItems = arrayListOf<ViewItemBase>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNameListBinding.inflate(inflater, container, false)

        mainViewModel.refreshNames()
        val nameList = binding.nameList
        with(nameList) {
            layoutManager = LinearLayoutManager(context)
            adapter = NameAdapter(nameItems)
        }
        mainViewModel.connectionStateData.observe(viewLifecycleOwner) {
            when (it) {
                ConnectionState.ERROR -> binding.connectionStatus.visibility = VISIBLE
                ConnectionState.SUCCESS -> binding.connectionStatus.visibility =
                    GONE
            }
        }

        mainViewModel.getNames().observe(viewLifecycleOwner) { names ->
            nameItems.clear()
            binding.swiperefresh.isRefreshing = false

            nameItems.addAll(names)
            logger.atWarning().log(nameItems.toString())
            binding.nameList.adapter?.notifyDataSetChanged()
        }

        binding.swiperefresh.setOnRefreshListener {
            mainViewModel.refreshNames()
        }

        return binding.root
    }

    companion object {
        private val logger: FluentLogger = FluentLogger.forEnclosingClass()
    }
}
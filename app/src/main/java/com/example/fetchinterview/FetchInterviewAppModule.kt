package com.example.fetchinterview

import com.example.fetchinterview.data.NameApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object FetchInterviewAppModule {
    private const val BASE_URL =
        "https://fetch-hiring.s3.amazonaws.com"
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(
            Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()

    @Singleton
    @Provides
    fun getNameApiService() : NameApiService {
       return retrofit.create(NameApiService::class.java)
    }
}
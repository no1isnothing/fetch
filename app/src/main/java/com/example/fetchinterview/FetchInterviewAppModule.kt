package com.example.fetchinterview

import android.content.Context
import androidx.room.Room
import com.example.fetchinterview.data.MainDatabase
import com.example.fetchinterview.data.NameApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object FetchInterviewAppModule {
    @Singleton
    @Provides
    fun provideMainDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context.applicationContext,
        MainDatabase::class.java,
        "main_database"
    ).build()

    @Singleton
    @Provides
    fun provideNameDao(database: MainDatabase) = database.nameDao()

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
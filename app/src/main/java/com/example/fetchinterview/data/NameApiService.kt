package com.example.fetchinterview.data

import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

/**
 * A web service to pull [NameItem]s from the Fetch hiring link.
 */
interface NameApiService {
    @GET("hiring.json")
    suspend fun getNames(): List<NameItem>
}
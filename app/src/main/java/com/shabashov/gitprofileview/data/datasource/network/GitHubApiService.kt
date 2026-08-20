package com.shabashov.gitprofileview.data.datasource.network

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


object RetrofitClient {
    private val contentType = "application/json".toMediaType()
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request()
            if (request.url.toString().contains("search/users")) {
                val modifiedUrl = request.url.toString() + "+in:login"
                val newRequest = request.newBuilder()
                    .url(modifiedUrl)
                    .build()
                chain.proceed(newRequest)
            } else {
                chain.proceed(request)
            }
        }
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com")
        .addConverterFactory(Json.asConverterFactory(contentType))
        .client(okHttpClient)
        .build()
}

interface GithubApiService {
    @GET("search/users")
    suspend fun findByLogin(
        @Query("q") login: String,
    ): UserResponse

    @GET("users/{login}")
    suspend fun getUserByLogin(
        @Path("login") login: String
    ): Response<User>
}

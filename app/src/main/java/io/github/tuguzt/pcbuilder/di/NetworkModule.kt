@file:Suppress("SameParameterValue")

package io.github.tuguzt.pcbuilder.di

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import io.github.tuguzt.pcbuilder.presentation.repository.net.backend.BackendAuthAPI
import io.github.tuguzt.pcbuilder.presentation.repository.net.backend.BackendOctopartAPI
import io.github.tuguzt.pcbuilder.presentation.repository.net.backend.BackendUsersAPI
import io.github.tuguzt.pcbuilder.presentation.view.userSharedPreferences
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.create

const val backendBaseUrl = "http://192.168.0.105:8080/" // "https://pc-builder-tuguzt.herokuapp.com/"

/**
 * Network module of the Koin DI.
 */
val networkModule = module {
    @OptIn(ExperimentalSerializationApi::class)
    single(named("json-converter-factory")) {
        val json: Json = get()
        json.asConverterFactory(MediaType.get("application/json"))
    }

    single { backendClient(androidContext()) }

    // Backend Octopart API (to replace previous Octopart API)
    single(named("retrofit-backend-octopart")) {
        retrofitAuth(get(), "${backendBaseUrl}octopart/", get(named("json-converter-factory")))
    }
    single { backendOctopartAPI(get(named("retrofit-backend-octopart"))) }

    // Backend Authentication API
    single(named("retrofit-backend-auth")) {
        retrofit(backendBaseUrl, get(named("json-converter-factory")))
    }
    single { backendAuthAPI(get(named("retrofit-backend-auth"))) }

    // Backend Users API
    single(named("retrofit-backend-users")) {
        retrofitAuth(get(), "${backendBaseUrl}users/", get(named("json-converter-factory")))
    }
    single { backendUsersAPI(get(named("retrofit-backend-users"))) }
}

private fun retrofit(baseUrl: String, converterFactory: Converter.Factory): Retrofit =
    Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(converterFactory)
        .build()

private fun backendClient(context: Context): OkHttpClient =
    OkHttpClient.Builder()
        .addInterceptor { chain ->
            val preferences = context.userSharedPreferences
            val token = preferences.getString("access_token", null).orEmpty()
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(request)
        }
        .build()

private fun retrofitAuth(
    client: OkHttpClient,
    baseUrl: String,
    converterFactory: Converter.Factory,
): Retrofit =
    Retrofit.Builder()
        .client(client)
        .baseUrl(baseUrl)
        .addConverterFactory(converterFactory)
        .build()

private fun backendOctopartAPI(retrofit: Retrofit): BackendOctopartAPI = retrofit.create()

private fun backendAuthAPI(retrofit: Retrofit): BackendAuthAPI = retrofit.create()

private fun backendUsersAPI(retrofit: Retrofit): BackendUsersAPI = retrofit.create()

@file:Suppress("SameParameterValue")

package io.github.tuguzt.pcbuilder.di

import android.content.Context
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import io.github.tuguzt.pcbuilder.presentation.repository.net.backend.BackendAuthAPI
import io.github.tuguzt.pcbuilder.presentation.repository.net.backend.BackendUsersAPI
import io.github.tuguzt.pcbuilder.presentation.view.userSharedPreferences
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.create

const val backendBaseUrl = "https://pc-builder-tuguzt.herokuapp.com/"

/**
 * Network module of the Koin DI.
 */
val networkModule = module {
    @OptIn(ExperimentalSerializationApi::class)
    single(named("json-converter-factory")) {
        val json: Json = get()
        json.asConverterFactory("application/json".toMediaType())
    }

    single { backendClient(androidContext()) }

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
        .addCallAdapterFactory(NetworkResponseAdapterFactory())
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
        .addCallAdapterFactory(NetworkResponseAdapterFactory())
        .build()

private fun backendAuthAPI(retrofit: Retrofit): BackendAuthAPI = retrofit.create()

private fun backendUsersAPI(retrofit: Retrofit): BackendUsersAPI = retrofit.create()

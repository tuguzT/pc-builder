@file:Suppress("SameParameterValue")

package io.github.tuguzt.pcbuilder.di

import android.content.Context
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import io.github.tuguzt.pcbuilder.BuildConfig
import io.github.tuguzt.pcbuilder.presentation.repository.net.BackendAuthAPI
import io.github.tuguzt.pcbuilder.presentation.repository.net.BackendUsersAPI
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

/**
 * Network module of the Koin DI.
 */
val networkModule = module {
    @OptIn(ExperimentalSerializationApi::class)
    single { Json.asConverterFactory("application/json".toMediaType()) }

    single { backendClient(androidContext()) }

    // Backend Authentication API
    single(named("retrofit-backend-auth")) {
        retrofit(BuildConfig.BACKEND_BASE_URL, get())
    }
    single { backendAuthAPI(get(named("retrofit-backend-auth"))) }

    // Backend Users API
    single(named("retrofit-backend-users")) {
        retrofit("${BuildConfig.BACKEND_BASE_URL}users/", get(), get())
    }
    single { backendUsersAPI(get(named("retrofit-backend-users"))) }
}

private fun retrofit(
    baseUrl: String,
    converterFactory: Converter.Factory,
    client: OkHttpClient? = null,
): Retrofit =
    Retrofit.Builder()
        .apply {
            client?.let { client(it) }
            baseUrl(baseUrl)
            addConverterFactory(converterFactory)
            addCallAdapterFactory(NetworkResponseAdapterFactory())
        }
        .build()

private fun backendClient(context: Context): OkHttpClient =
    OkHttpClient.Builder()
        .addInterceptor { chain ->
            val preferences = context.userSharedPreferences
            val token = preferences.getString("access_token", null).orEmpty()
            val request = chain
                .request()
                .newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(request)
        }
        .build()

private fun backendAuthAPI(retrofit: Retrofit): BackendAuthAPI = retrofit.create()

private fun backendUsersAPI(retrofit: Retrofit): BackendUsersAPI = retrofit.create()

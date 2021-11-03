package io.github.tuguzt.pcbuilder.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import io.github.tuguzt.pcbuilder.presentation.repository.net.backend.BackendAPI
import io.github.tuguzt.pcbuilder.presentation.repository.net.octopart.OctopartAPI
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create

/**
 * Network module of the Koin DI.
 */
val networkModule = module {
    single(qualifier = named("retrofit-octopart")) {
        retrofit("https://octopart.com/api/", get())
    }
    single { octopartAPI(get(qualifier = named("retrofit-octopart"))) }

    single(qualifier = named("retrofit-backend")) {
        retrofit("https://pc-builder-tuguzt.herokuapp.com", get())
    }
    single { backendAPI(get(qualifier = named("retrofit-backend"))) }
}

@OptIn(ExperimentalSerializationApi::class)
private fun retrofit(baseUrl: String, json: Json): Retrofit =
    Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(json.asConverterFactory(MediaType.get("application/json")))
        .build()

private fun octopartAPI(retrofit: Retrofit): OctopartAPI = retrofit.create()

private fun backendAPI(retrofit: Retrofit): BackendAPI = retrofit.create()

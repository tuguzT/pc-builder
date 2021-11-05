package io.github.tuguzt.pcbuilder.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import io.github.tuguzt.pcbuilder.presentation.repository.net.backend.BackendAPI
import io.github.tuguzt.pcbuilder.presentation.repository.net.octopart.OctopartAPI
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
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
    single(named("json-converter-factory")) {
        val json: Json = get()
        json.asConverterFactory(MediaType.get("application/json"))
    }

    single(named("retrofit-octopart")) {
        retrofit("https://octopart.com/api/",
            get(named("json-converter-factory")))
    }
    single { octopartAPI(get(named("retrofit-octopart"))) }

    single(named("retrofit-backend")) {
        retrofit("https://pc-builder-tuguzt.herokuapp.com",
            get(named("json-converter-factory")))
    }
    single { backendAPI(get(named("retrofit-backend"))) }
}

private fun retrofit(baseUrl: String, converterFactory: Converter.Factory): Retrofit =
    Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(converterFactory)
        .build()

private fun octopartAPI(retrofit: Retrofit): OctopartAPI = retrofit.create()

private fun backendAPI(retrofit: Retrofit): BackendAPI = retrofit.create()

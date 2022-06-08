package io.github.tuguzt.pcbuilder.presentation.di

import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.tuguzt.pcbuilder.data.dataOrNull
import io.github.tuguzt.pcbuilder.data.datasource.remote.api.BackendAuthAPI
import io.github.tuguzt.pcbuilder.data.datasource.remote.api.BackendBuildAPI
import io.github.tuguzt.pcbuilder.data.datasource.remote.api.BackendComponentsAPI
import io.github.tuguzt.pcbuilder.data.datasource.remote.api.BackendUsersAPI
import io.github.tuguzt.pcbuilder.data.datasource.remote.impl.RemoteBuildDataSource
import io.github.tuguzt.pcbuilder.data.datasource.remote.impl.RemoteComponentDataSource
import io.github.tuguzt.pcbuilder.data.repository.UserTokenRepository
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BackendModule {
    private const val backendBaseUrl = "https://pc-builder-tuguzt.herokuapp.com/"

    @Provides
    @Singleton
    @OptIn(ExperimentalSerializationApi::class)
    fun provideConverterFactory(json: Json): Converter.Factory =
        json.asConverterFactory("application/json".toMediaType())

    @Provides
    fun provideAuthInterceptorClient(tokenRepository: UserTokenRepository): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val token = runBlocking { tokenRepository.getToken() }.dataOrNull()?.token
                val request = chain
                    .request()
                    .newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                chain.proceed(request)
            }
            .build()

    @Provides
    @AuthRetrofit
    fun provideAuthRetrofit(converterFactory: Converter.Factory): Retrofit =
        retrofit(backendBaseUrl, converterFactory)

    @Provides
    @SimpleRetrofit
    fun provideRetrofit(
        converterFactory: Converter.Factory,
        authInterceptorClient: OkHttpClient,
    ): Retrofit =
        retrofit(backendBaseUrl, converterFactory, authInterceptorClient)

    @Provides
    fun provideBackendAuthAPI(@AuthRetrofit retrofit: Retrofit): BackendAuthAPI = retrofit.create()

    @Provides
    fun provideBackendUsersAPI(@SimpleRetrofit retrofit: Retrofit): BackendUsersAPI =
        retrofit.create()

    @Provides
    fun provideBackendBuildAPI(@SimpleRetrofit retrofit: Retrofit): BackendBuildAPI =
        retrofit.create()

    @Provides
    fun provideBackendComponentsAPI(@SimpleRetrofit retrofit: Retrofit): BackendComponentsAPI =
        retrofit.create()

    @Provides
    fun provideRemoteComponentDataSource(backendComponentsAPI: BackendComponentsAPI): RemoteComponentDataSource =
        RemoteComponentDataSource(backendComponentsAPI)

    @Provides
    fun provideRemoteBuildDataSource(backendBuildAPI: BackendBuildAPI): RemoteBuildDataSource =
        RemoteBuildDataSource(backendBuildAPI)

    @JvmStatic
    @Suppress("NOTHING_TO_INLINE")
    private inline fun retrofit(
        baseUrl: String,
        converterFactory: Converter.Factory,
        client: OkHttpClient? = null,
    ): Retrofit =
        Retrofit.Builder()
            .apply { client?.let { client(it) } }
            .baseUrl(baseUrl)
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .build()
}

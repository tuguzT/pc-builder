package io.github.tuguzt.pcbuilder.data.datasource.remote.api

import io.github.tuguzt.pcbuilder.data.datasource.remote.BackendResponse
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.data.PolymorphicComponent
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserData
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Backend API for user management (requires access token).
 */
interface BackendUsersAPI {
    @GET("users/all")
    suspend fun getAll(): BackendResponse<List<UserData>>

    @GET("users/current")
    suspend fun current(): BackendResponse<UserData>

    @GET("users/current/favorites/all")
    suspend fun allFavoriteComponents(): BackendResponse<List<PolymorphicComponent>>

    @POST("users/current/favorites/add/{componentId}")
    suspend fun addToFavorites(@Path("componentId") componentId: NanoId): BackendResponse<PolymorphicComponent>

    @POST("users/current/favorites/remove/{componentId}")
    suspend fun removeFromFavorites(@Path("componentId") componentId: NanoId): BackendResponse<PolymorphicComponent>

    @GET("users/id/{id}")
    suspend fun findById(@Path("id") id: NanoId): BackendResponse<UserData?>

    @GET("users/username/{username}")
    suspend fun findByUsername(@Path("username") username: String): BackendResponse<UserData?>
}

package io.github.tuguzt.pcbuilder.presentation.repository.net.backend

import io.github.tuguzt.pcbuilder.presentation.repository.net.octopart.OctopartAPI
import retrofit2.Call
import retrofit2.http.GET

/**
 * Definition of the REST API for the remote `PC Builder` backend server.
 */
interface BackendAPI {
    /**
     * Retrieves the PAI token for the [OctopartAPI].
     */
    @GET("octopart/token")
    fun getOctopartToken(): Call<String>
}

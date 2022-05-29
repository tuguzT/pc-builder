package io.github.tuguzt.pcbuilder.viewmodel.account

import com.haroldadmin.cnradapter.NetworkResponse
import io.github.tuguzt.pcbuilder.repository.backend.BackendCompletableResponse
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

/**
 * Create unknown error response to be handled bu application.
 */
fun makeUnknownError(message: String): BackendCompletableResponse {
    val error = Exception(message)
    val errorResponse = Response.error<Nothing>(500, "".toResponseBody())
    return NetworkResponse.UnknownError(error, errorResponse)
}

/**
 * Create successful response.
 */
fun makeSuccess(): BackendCompletableResponse {
    val response = Response.success(Unit)
    return NetworkResponse.Success(Unit, response)
}

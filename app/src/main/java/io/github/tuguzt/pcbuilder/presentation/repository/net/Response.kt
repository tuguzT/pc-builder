package io.github.tuguzt.pcbuilder.presentation.repository.net

import com.haroldadmin.cnradapter.CompletableResponse
import com.haroldadmin.cnradapter.NetworkResponse
import io.github.tuguzt.pcbuilder.presentation.model.BackendError
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

typealias BackendResponse<T> = NetworkResponse<T, BackendError>

typealias BackendCompletableResponse = CompletableResponse<BackendError>

fun <T> makeError(message: String): NetworkResponse.Error<T, BackendError> {
    val responseBody = message.toResponseBody()
    val errorResponse = Response.error<String>(500, responseBody)
    val error = Exception(message)
    return NetworkResponse.UnknownError(error, errorResponse)
}

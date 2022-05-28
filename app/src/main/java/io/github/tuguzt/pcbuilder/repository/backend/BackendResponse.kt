package io.github.tuguzt.pcbuilder.repository.backend

import com.haroldadmin.cnradapter.CompletableResponse
import com.haroldadmin.cnradapter.NetworkResponse
import io.github.tuguzt.pcbuilder.model.BackendError

typealias BackendResponse<S> = NetworkResponse<S, BackendError>

typealias BackendCompletableResponse = CompletableResponse<BackendError>

package io.github.tuguzt.pcbuilder.presentation.repository.net

import com.haroldadmin.cnradapter.NetworkResponse
import io.github.tuguzt.pcbuilder.presentation.model.BackendError

typealias BackendResponse<T> = NetworkResponse<T, BackendError>

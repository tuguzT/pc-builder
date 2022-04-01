package io.github.tuguzt.pcbuilder.presentation.repository.net.backend

import com.haroldadmin.cnradapter.NetworkResponse
import io.github.tuguzt.pcbuilder.presentation.repository.net.backend.model.BackendError

typealias BackendResponse<T> = NetworkResponse<T, BackendError>

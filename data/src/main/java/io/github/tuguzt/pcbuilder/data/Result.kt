package io.github.tuguzt.pcbuilder.data

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

sealed interface Result<S, E> {
    data class Success<S, E>(val data: S) : Result<S, E>

    data class Error<S, E>(val error: E?, val throwable: Throwable?) : Result<S, E>
}

fun <S, E> Result<S, E>.dataOrNull(): S? = when (this) {
    is Result.Error -> null
    is Result.Success -> data
}

fun <S, E> success(data: S): Result.Success<S, E> =
    Result.Success(data)

fun <S, E> error(error: E?, throwable: Throwable?): Result.Error<S, E> =
    Result.Error(error, throwable)

@OptIn(ExperimentalContracts::class)
fun <S, E, N> Result<S, E>.mapSuccess(transform: (S) -> N): Result<N, E> {
    contract {
        callsInPlace(transform, InvocationKind.AT_MOST_ONCE)
    }
    return when (this) {
        is Result.Error -> cast()
        is Result.Success -> map(transform)
    }
}

@OptIn(ExperimentalContracts::class)
fun <S, E, N> Result.Success<S, E>.map(transform: (S) -> N): Result.Success<N, E> {
    contract {
        callsInPlace(transform, InvocationKind.EXACTLY_ONCE)
    }
    return Result.Success(transform(data))
}

fun <S, E, N> Result.Success<S, E>.cast(): Result.Success<S, N> {
    @Suppress("UNCHECKED_CAST")
    return this as Result.Success<S, N>
}

fun <S, E, N> Result.Error<S, E>.cast(): Result.Error<N, E> {
    @Suppress("UNCHECKED_CAST")
    return this as Result.Error<N, E>
}

/**
 * Converts [kotlin.Result] to data layer [Result].
 */
fun <S, E> kotlin.Result<S>.toResult(): Result<S, E> = when (val data = getOrNull()) {
    null -> Result.Error(error = null, throwable = exceptionOrNull())
    else -> Result.Success(data)
}

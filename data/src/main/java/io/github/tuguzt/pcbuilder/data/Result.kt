package io.github.tuguzt.pcbuilder.data

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

fun <S, E, N> Result.Success<S, E>.map(): Result.Success<S, N> {
    @Suppress("UNCHECKED_CAST")
    return this as Result.Success<S, N>
}

fun <S, E, N> Result.Error<S, E>.map(): Result.Error<N, E> {
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

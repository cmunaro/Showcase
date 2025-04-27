package com.example.showcase.base

sealed interface Async<out T : Any> {
    data object Uninitialized : Async<Nothing>
    data class Loading<T : Any>(val value: T?) : Async<T>
    data class Success<T : Any>(val value: T) : Async<T>
    data class Failure(val throwable: Throwable) : Async<Nothing>
}

fun <T : Any, R : Any> Async<T>.mapValue(mapper: (T) -> R): Async<R> = when (this) {
    is Async.Loading -> Async.Loading(value?.let(mapper))
    is Async.Success -> Async.Success(mapper(value))
    is Async.Failure -> Async.Failure(throwable)
    Async.Uninitialized -> Async.Uninitialized
}

inline fun <reified T : Any> Async<T>.getOrElse(default: T): T =
    getOrNull() ?: default

inline fun <reified T : Any> Async<T>.getOrThrow(): T =
    getOrNull() ?: throw NotAValueAsync(this)

inline fun <reified T : Any> Async<T>.getOrNull(): T? = when (this) {
    is Async.Failure, Async.Uninitialized -> null
    is Async.Loading -> value
    is Async.Success -> value
}

data class NotAValueAsync(val value: Async<*>) :
    Throwable(message = "Async $value doesn't contains a value")
package com.example.showcase.base

import kotlin.coroutines.cancellation.CancellationException

inline fun <R> runCatchingNonCancellation(block: () -> R): Result<R> =
    try {
        Result.success(block())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Throwable) {
        Result.failure(e)
    }
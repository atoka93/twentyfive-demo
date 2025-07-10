@file:Suppress("ClassName")

package com.attilaszabo.twentyfivedemo.common.domain

sealed class CustomResult<out T> {
    data class success<out T>(val data: T) : CustomResult<T>()
    data class failure<out T>(val exception: Throwable? = null) : CustomResult<T>()
}

val <T> CustomResult<T>.isSuccess: Boolean
    get() = (this as? CustomResult.success)?.data != null

val <T> CustomResult<T>.dataOrNull: T?
    get() = (this as? CustomResult.success)?.data

fun <T> CustomResult<T>.getOrNull(): T? = dataOrNull

val <T> CustomResult<T>.isFailure: Boolean
    get() = !isSuccess

val <T> CustomResult<T>.exceptionOrNull: Throwable?
    get() = (this as? CustomResult.failure)?.exception

fun <T> CustomResult<T>.exceptionOrNull(): Throwable? = exceptionOrNull

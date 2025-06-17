package com.attilaszabo.twentyfivedemo.commondomain

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error<out T>(val exception: Throwable? = null) : Result<T>()
}

fun <T> Result<T>.isSuccess(): Boolean = (this as? Result.Success)?.data != null
fun <T> Result<T>.successOrNull(): T? = (this as? Result.Success)?.data
fun <T> Result<T>.isError(): Boolean = !isSuccess()
fun <T> Result<T>.errorOrNull(): Throwable? = (this as? Result.Error)?.exception

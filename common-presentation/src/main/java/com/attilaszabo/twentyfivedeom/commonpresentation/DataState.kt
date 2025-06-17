package com.attilaszabo.twentyfivedeom.commonpresentation

sealed class DataState<out T> {
    data object Initial : DataState<Nothing>()
    data object Loading : DataState<Nothing>()
    data class Success<T>(val data: T) : DataState<T>()
    data class Error(val message: String) : DataState<Nothing>()
    data object Empty : DataState<Nothing>()
}

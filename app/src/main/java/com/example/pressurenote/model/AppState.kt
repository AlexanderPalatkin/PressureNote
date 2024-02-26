package com.example.pressurenote.model

sealed class AppState<out T, out E> {
    data class Success<T>(val data: T) : AppState<T, Nothing>()
    data object Loading : AppState<Nothing, Nothing>()
    data class Error<E>(val error: E) : AppState<Nothing, E>()
}
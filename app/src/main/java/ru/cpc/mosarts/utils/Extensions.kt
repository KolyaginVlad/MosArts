package ru.cpc.mosarts.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

inline fun <T : Any, F : Any> Flow<List<T>>.mapIterable(
    crossinline transform: (value: T) -> F
): Flow<List<F>> =
    this.map { it.map(transform) }

fun <T> Flow<T>.mapToResult() =
    map { Result.success(it) }
        .catch { emit(Result.failure(it)) }
package ru.cpc.mosarts.utils

import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.Navigator
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.Direction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import ru.cpc.mosarts.ui.destinations.RegistrationScreenDestination

inline fun <T : Any, F : Any> Flow<List<T>>.mapIterable(
    crossinline transform: (value: T) -> F
): Flow<List<F>> =
    this.map { it.map(transform) }

fun <T> Flow<T>.mapToResult() =
    map { Result.success(it) }
        .catch { emit(Result.failure(it)) }

fun DestinationsNavigator.navigateWithClearBackStack(
    direction: Direction,
    onlyIfResumed: Boolean = false,
    builder: NavOptionsBuilder.() -> Unit = {}
) =
    navigate(direction, onlyIfResumed) {
        popBackStack()
        builder()
    }
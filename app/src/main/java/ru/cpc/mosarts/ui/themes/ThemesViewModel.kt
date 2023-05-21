package ru.cpc.mosarts.ui.themes

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import ru.cpc.mosarts.domain.usecases.GetThemesUseCase
import ru.cpc.mosarts.domain.usecases.SendSelectedThemesUseCase
import ru.cpc.mosarts.ui.themes.models.ThemeUiData
import ru.cpc.mosarts.ui.themes.models.mapToDomain
import ru.cpc.mosarts.ui.themes.models.mapToUi
import ru.cpc.mosarts.utils.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ThemesViewModel @Inject constructor(
    getThemesUseCase: GetThemesUseCase,
    private val sendSelectedThemesUseCase: SendSelectedThemesUseCase
): BaseViewModel<ThemesScreenState, ThemesScreenEvent>(ThemesScreenState()) {

    init {
        launchViewModelScope {
            getThemesUseCase().fold(
                onFailure = {
                    handleException(it)
                    sendEvent(ThemesScreenEvent.Error)
                },
                onSuccess = { list ->
                    updateState {
                        it.copy(
                            isLoading = false,
                            themes = list.map { it.mapToUi(false) }.toPersistentList()
                        )
                    }
                }
            )
        }
    }

    fun onSelectTheme(theme: ThemeUiData, isSelected: Boolean) {
        val newList = currentState.themes.toMutableList().apply {
            val index = indexOf(theme)
            removeAt(index)
            add(index, theme.copy(isSelected = isSelected))
        }
        updateState {
            it.copy(
                themes = newList.toPersistentList()
            )
        }
    }

    fun onDone() {
        launchViewModelScope {
            updateState {
                it.copy(isLoading = true)
            }
            sendSelectedThemesUseCase(
                currentState.themes.filter { it.isSelected }.map { it.mapToDomain() }
            )
            updateState {
                it.copy(isLoading = false)
            }
        }
    }
}
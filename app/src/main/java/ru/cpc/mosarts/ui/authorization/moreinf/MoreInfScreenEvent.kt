package ru.cpc.mosarts.ui.authorization.moreinf

import ru.cpc.mosarts.utils.base.Event

sealed class MoreInfScreenEvent : Event() {
    object GoToTest : MoreInfScreenEvent()

    class ShowToast(val text: String) : MoreInfScreenEvent()
}
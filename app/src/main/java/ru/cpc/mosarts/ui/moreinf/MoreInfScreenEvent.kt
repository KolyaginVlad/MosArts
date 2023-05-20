package ru.cpc.mosarts.ui.moreinf

import ru.cpc.mosarts.utils.base.Event

sealed class MoreInfScreenEvent : Event() {
	class ShowToast(val text: String) : MoreInfScreenEvent()
}
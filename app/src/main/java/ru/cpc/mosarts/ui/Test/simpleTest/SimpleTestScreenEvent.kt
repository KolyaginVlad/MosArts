package ru.cpc.mosarts.ui.test.simpleTest

import ru.cpc.mosarts.utils.base.Event

sealed class SimpleTestScreenEvent : Event() {
	class ShowToast(val text: String) : SimpleTestScreenEvent()
	
}
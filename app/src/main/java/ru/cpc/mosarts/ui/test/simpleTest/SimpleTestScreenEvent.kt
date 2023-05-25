package ru.cpc.mosarts.ui.test.simpleTest

import ru.cpc.mosarts.utils.base.Event

sealed class SimpleTestScreenEvent : Event() {
	
	class Error(val text: String) : SimpleTestScreenEvent()
	
	class WrongAnswer(val explain: String?) : SimpleTestScreenEvent()
	
	class RightAnswer(val explain: String?) : SimpleTestScreenEvent()
	
	class StartPlaying(val source: String?) : SimpleTestScreenEvent()
}
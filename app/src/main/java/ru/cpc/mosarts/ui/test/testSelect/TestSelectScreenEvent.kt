package ru.cpc.mosarts.ui.test.testSelect

import ru.cpc.mosarts.domain.models.Difficulty
import ru.cpc.mosarts.domain.models.NamesOfTest
import ru.cpc.mosarts.utils.base.Event


sealed class TestSelectScreenEvent : Event() {
	class Error(val text: String) : TestSelectScreenEvent()
	
	object TestSelected : TestSelectScreenEvent()
	class GoToTest(val test: NamesOfTest, val difficulty: Difficulty) : TestSelectScreenEvent()
}
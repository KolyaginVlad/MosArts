package ru.cpc.mosarts.ui.test.testSelect

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.cpc.mosarts.domain.models.Difficulty
import ru.cpc.mosarts.domain.models.NamesOfTest
import ru.cpc.mosarts.utils.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class TestSelectViewModel @Inject constructor(
) : BaseViewModel<TestSelectScreenState, TestSelectScreenEvent>(TestSelectScreenState()) {

	fun goToTest(difficulty: Difficulty){
		launchViewModelScope {
			currentState.test?.let { TestSelectScreenEvent.GoToTest(it,difficulty) }
				?.let { sendEvent(it) }
		}
	}
	
	fun onVariantClick(namesOfTest: NamesOfTest){
		launchViewModelScope {
			updateState { it.copy(test = namesOfTest) }
			sendEvent(TestSelectScreenEvent.TestSelected)
		}
	}
}

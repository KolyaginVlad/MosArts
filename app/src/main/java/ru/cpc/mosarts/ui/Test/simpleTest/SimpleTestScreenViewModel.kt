package ru.cpc.mosarts.ui.test.simpleTest

import androidx.compose.runtime.toMutableStateList
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.cpc.mosarts.domain.models.UserAnswer
import ru.cpc.mosarts.domain.usecases.SimpleTestUseCase
import ru.cpc.mosarts.utils.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel

class SimpleTestScreenViewModel @Inject constructor(
	private val simpleTestUseCase: SimpleTestUseCase,
) : BaseViewModel<SimpleTestScreenState, SimpleTestScreenEvent>(SimpleTestScreenState()) {
	
	init {
		launchViewModelScope {
			getTest()
		}
	}
	
	fun onAnswerChange(answer: UserAnswer) {
		updateState { testScreenState ->
			testScreenState.copy(
				answers = testScreenState.answers.map {
					if (it.questionId == answer.questionId) {
						answer
					} else it
				}.toMutableStateList()
			)
		}
	}
	
	private suspend fun getTest() {
		simpleTestUseCase(Unit).fold(
			onFailure = {
				sendEvent(SimpleTestScreenEvent.ShowToast(it.message ?: "Something went wrong"))
			}, onSuccess = { questions ->
				val answers = arrayListOf<UserAnswer>()
				questions.forEach {
					answers.add(
						UserAnswer(
							questionId = it.questionId,
							optionsAnswer = arrayListOf()
						)
					)
				}
				updateState {
					it.copy(
						questions = questions,
						answers = answers.toMutableStateList()
					)
				}
			}
		)
	}
	
}


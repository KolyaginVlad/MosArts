package ru.cpc.mosarts.ui.test.simpleTest

import androidx.compose.runtime.toMutableStateList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import ru.cpc.mosarts.domain.models.TestResults
import ru.cpc.mosarts.domain.models.UserAnswer
import ru.cpc.mosarts.domain.usecases.GetSimpleTestUseCase
import ru.cpc.mosarts.domain.usecases.SendSimpleTestUseCase
import ru.cpc.mosarts.utils.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel

class SimpleTestScreenViewModel @Inject constructor(
	private val getSimpleTestUseCase: GetSimpleTestUseCase,
	private val sendSimpleTestUseCase: SendSimpleTestUseCase,
) : BaseViewModel<SimpleTestScreenState, SimpleTestScreenEvent>(SimpleTestScreenState()) {
	
	init {
		launchViewModelScope {
			getTest()
		}
	}
	
	fun onAnswerChange(answer: UserAnswer) {
		if (currentState.currentQuestion?.let { currentState.answers[it] } == UserAnswer()) {
			launchViewModelScope {
				updateState { testScreenState ->
					testScreenState.copy(
						answers = testScreenState.answers.mapIndexed { index, answe ->
							if (index == testScreenState.currentQuestion) {
								answer
							} else answe
						}.toMutableStateList()
					)
				}
				if (checkAnswer(answer)) {
					sendEvent(
						SimpleTestScreenEvent.RightAnswer(curQuestion()?.explain)
					)
					curQuestion()?.let { question ->
						updateState { it.copy(results = TestResults(points = it.results.points + question.cost)) }
					}
				} else sendEvent(
					SimpleTestScreenEvent.WrongAnswer(curQuestion()?.explain)
				)
				delay(2500)
				nextQuestion()
			}
		}
	}
	
	private fun curQuestion() = currentState.currentQuestion?.let {
		currentState.questions[it]
	}
	
	fun checkAnswer(answer: UserAnswer) =
		currentState.currentQuestion?.let {
			currentState.questions[it].rightAnswer == answer
		} ?: false
	
	
	private suspend fun getTest() {
		getSimpleTestUseCase().fold(
			onFailure = {
				sendEvent(SimpleTestScreenEvent.Error(it.message ?: "Something went wrong"))
			}, onSuccess = { questions ->
				val answers = arrayListOf<UserAnswer>()
				questions.forEach {
					answers.add(
						UserAnswer()
					)
				}
				updateState {
					it.copy(
						questions = questions,
						answers = answers.toMutableStateList(),
						currentQuestion = 0,
					)
				}
			}
		)
	}
	
	fun sendTest() {
		logger.debug(currentState.results.points.toString())
		updateState {
			it.copy(isLoading = true)
		}
		launchViewModelScope {
			sendSimpleTestUseCase(currentState.results).fold(
				onFailure = {
					handleException(it)
					sendEvent(
						SimpleTestScreenEvent.Error("Error")
					)
				}, onSuccess = {
					// TODO:
					updateState {
						it.copy(isLoading = false, finished = true)
					}
				}
			)
		}
	}
	
	fun nextQuestion() {
		updateState {
			it.copy(isLoading = true)
		}
		updateState {
			it.copy(
				currentQuestion =
				if (it.questions.size - 1 > it.currentQuestion ?: 0)
					it.currentQuestion?.plus(1)
				else it.currentQuestion
			
			)
		}
	}
	
	fun previousQuestion() {
		updateState {
			it.copy(isLoading = true)
		}
		updateState {
			it.copy(
				currentQuestion =
				if (0 < it.currentQuestion ?: 0)
					it.currentQuestion?.minus(1)
				else it.currentQuestion
			
			)
		}
	}
	
}


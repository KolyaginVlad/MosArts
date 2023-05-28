package ru.cpc.mosarts.ui.test.simpleTest

import androidx.compose.runtime.toMutableStateList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import ru.cpc.mosarts.domain.models.Difficulty
import ru.cpc.mosarts.domain.models.NamesOfTest
import ru.cpc.mosarts.domain.models.TestParams
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

    fun init(namesOfTest: NamesOfTest, difficulty: Difficulty) {
        launchViewModelScope {
            getTest(namesOfTest, difficulty)
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
				if (curQuestion()?.explain.isNullOrBlank()) {
					delay(1500)
					nextQuestion()
				} else {
					updateState {
						it.copy(openExplainDialog = true)
					}
				}
            }
        }
    }

    private fun curQuestion() = currentState.currentQuestion?.let {
        currentState.questions[it]
    }

    private fun checkAnswer(answer: UserAnswer) =
        currentState.currentQuestion?.let {
            currentState.questions[it].rightAnswer == answer
        } ?: false


    private suspend fun getTest(namesOfTest: NamesOfTest, difficulty: Difficulty) {
        getSimpleTestUseCase(TestParams(namesOfTest, difficulty)).fold(
			onFailure = {
				sendEvent(SimpleTestScreenEvent.Error(it.message ?: "Something went wrong"))
			}, onSuccess = { questions ->
				val answers = arrayListOf<UserAnswer>()
				questions.forEach { _ ->
					answers.add(
						UserAnswer()
					)
				}
				updateState {
					it.copy(
						questions = questions,
						answers = answers.toMutableStateList(),
						currentQuestion = 0,
						isLoading = false
					)
				}
			}
		)
	}

	fun sendTest() {
		updateState {
			it.copy(isLoading = true, openExplainDialog = false)
		}
		launchViewModelScope {
			sendSimpleTestUseCase(currentState.results).fold(
				onFailure = {
					handleException(it)
					sendEvent(
						SimpleTestScreenEvent.Error("Error")
					)
					updateState {
						it.copy(isLoading = false)
					}
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
		if (currentState.questions.size - 1 > currentState.currentQuestion ?: 0) {
			updateState {
				it.copy(
					currentQuestion = it.currentQuestion?.plus(1),
					openExplainDialog = false
				)
			}
        } else sendTest()
    }

    fun startPlayer(source: String) {
        launchViewModelScope {
            sendEvent(SimpleTestScreenEvent.StartPlaying(source))
        }
        logger.debug(currentState.audioPlayer.getDataSource() ?: "Sourse")
    }

    fun previousQuestion() {
        updateState {
            it.copy(
                currentQuestion =
                if (0 < it.currentQuestion ?: 0)
                    it.currentQuestion?.minus(1)
                else it.currentQuestion

            )
        }
    }

    fun onDismissExplain() {
		nextQuestion()
    }

	fun onBackToTests() {
		trySendEvent(SimpleTestScreenEvent.BackToTests)
	}
}


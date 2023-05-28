package ru.cpc.mosarts.ui.test.simpleTest

import android.media.AudioAttributes
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import ru.cpc.mosarts.R
import ru.cpc.mosarts.domain.models.Difficulty
import ru.cpc.mosarts.domain.models.NamesOfTest
import ru.cpc.mosarts.domain.models.UserAnswer
import ru.cpc.mosarts.ui.activities.utils.MainNavGraph
import ru.cpc.mosarts.ui.test.simpleTest.views.ExplainDialog
import ru.cpc.mosarts.ui.test.simpleTest.views.Results
import ru.cpc.mosarts.ui.test.simpleTest.views.SimpleTestQuestion

@Destination
@Composable
fun SimpleTestScreen(
	navigator: DestinationsNavigator,
	viewModel: SimpleTestScreenViewModel = hiltViewModel(),
	test: NamesOfTest,
	difficulty: Difficulty
) {
    val explainText = remember { mutableStateOf("") }
	val state by viewModel.screenState.collectAsStateWithLifecycle()
	val openExplainDialog = remember {
		state.openExplainDialog
	}
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.init(namesOfTest = test, difficulty = difficulty)
		viewModel.event.collect {
			when (it) {
				is SimpleTestScreenEvent.Error -> Toast.makeText(
					context, it.text, Toast.LENGTH_LONG
				).show()

				is SimpleTestScreenEvent.WrongAnswer -> {
					it.explain?.let { explain ->
						explainText.value = explain
                    }
                }

                is SimpleTestScreenEvent.RightAnswer -> {
                    it.explain?.let { explain ->
                        explainText.value = explain
                    }
                }

                is SimpleTestScreenEvent.StartPlaying -> {
                    val player = state.audioPlayer
					if (player.getDataSource() != it.source) {
						player.reset()
						player.setAudioAttributes(
							AudioAttributes.Builder()
								.setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
								.build()
						)
						player.setDataSource(it.source)
						player.prepareAsync()
					} else player.start()

				}
			}
		}
	}
	if (openExplainDialog.value) {
		ExplainDialog(text = explainText.value, openDialogCustom = openExplainDialog)
    }
	if (state.isLoading) {
		Box(
			modifier = Modifier
				.fillMaxSize(),
			contentAlignment = Alignment.Center
		) {
			CircularProgressIndicator()
		}
	} else {
		SimpleTestScreenContent(
			state = state,
			onAnswerChange = viewModel::onAnswerChange,
			sendTest = viewModel::sendTest,
			nextQuestion = viewModel::nextQuestion,
			previousQuestion = viewModel::previousQuestion,
			startPlayer = viewModel::startPlayer
		)
	}
}

@Composable
fun SimpleTestScreenContent(
    state: SimpleTestScreenState,
    onAnswerChange: (UserAnswer) -> Unit,
    sendTest: () -> Unit,
    nextQuestion: () -> Unit,
    previousQuestion: () -> Unit,
    startPlayer: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {
        Card(
            shape = RoundedCornerShape(5),
            elevation = 10.dp,
            modifier = Modifier.padding(20.dp)
        ) {
            Column(
                modifier = modifier.padding(16.dp),
            ) {
                if (!state.finished) {
                    state.currentQuestion?.let {
                        state.questions[it].let { question ->
                            SimpleTestQuestion(
                                question = question,
                                answer = state.answers[it],
                                onAnswerChange = onAnswerChange,
                                modifier = Modifier.fillMaxWidth(),
                                player = state.audioPlayer,
                                startPlayer = startPlayer
                            )
                        }
                    }
                    /*	Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Button(onClick = previousQuestion) {
                                Text(text = stringResource(id = R.string.previous_question))
                            }

                            Button(onClick = nextQuestion) {
                                Text(text = stringResource(id = R.string.next_question))
                            }
                        }*/
                    Button(onClick = sendTest, modifier = Modifier.align(CenterHorizontally)) {
                        Text(text = stringResource(id = R.string.finish_test))
                    }
                } else {
                    Results(points = state.results.points)
                }
            }
        }
    }
}
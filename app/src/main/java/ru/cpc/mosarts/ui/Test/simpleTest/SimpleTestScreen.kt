package ru.cpc.mosarts.ui.test.simpleTest

import android.media.AudioAttributes
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import ru.cpc.mosarts.domain.models.UserAnswer
import ru.cpc.mosarts.ui.test.views.Results
import ru.cpc.mosarts.ui.test.views.SimpleTestQuestion

@Destination
@Composable
fun SimpleTestScreen(
	navigator: DestinationsNavigator,
	viewModel: SimpleTestScreenViewModel = hiltViewModel()
) {
	val state by viewModel.screenState.collectAsStateWithLifecycle()
	val context = LocalContext.current
	LaunchedEffect(Unit) {
		viewModel.event.collect {
			when (it) {
				is SimpleTestScreenEvent.Error -> Toast.makeText(
					context, it.text, Toast.LENGTH_LONG
				).show()
				
				is SimpleTestScreenEvent.WrongAnswer -> {
					it.explain?.let { explain ->
						Toast.makeText(
							context, explain, Toast.LENGTH_LONG
						).show()
					}
				}
				
				is SimpleTestScreenEvent.RightAnswer -> {
					it.explain?.let { explain ->
						Toast.makeText(
							context, explain, Toast.LENGTH_LONG
						).show()
					}
				}
				
				is SimpleTestScreenEvent.StartPlaying -> {
					var player = state.audioPlayer
					if (player.getDataSource() != it.source) {
						player.reset()
						player.setAudioAttributes(
							AudioAttributes.Builder()
								.setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
								.build()
						)
						player.setDataSource(it.source)
						player.setOnPreparedListener { player.start() }
						player.prepareAsync()
					} else player.start()
					
				}
			}
		}
	}
	SimpleTestScreenContent(
		state = state,
		onAnswerChange = viewModel::onAnswerChange,
		sendTest = viewModel::sendTest,
		nextQuestion = viewModel::nextQuestion,
		previousQuestion = viewModel::previousQuestion,
		startPlayer = viewModel::startPlayer
	)
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
	Scaffold(
		topBar = {
			TopAppBar(title = { Text(stringResource(id = R.string.app_name)) })
		}
	) {
		Column(
			modifier = modifier
				.padding(it)
				.fillMaxSize(),
			verticalArrangement = Arrangement.Center,
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Card(
				shape = RoundedCornerShape(5),
				elevation = 10.dp,
				modifier = Modifier.padding(20.dp)
			) {
				Column(
					modifier = modifier,
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
}
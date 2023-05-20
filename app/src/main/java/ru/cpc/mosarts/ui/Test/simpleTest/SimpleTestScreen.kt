package ru.cpc.mosarts.ui.test.simpleTest

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import ru.cpc.mosarts.domain.models.UserAnswer
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
				else -> {}
			}
		}
	}
	SimpleTestScreenContent(
		state = state,
		onAnswerChange = viewModel::onAnswerChange
	)
}

@Composable
fun SimpleTestScreenContent(
	state: SimpleTestScreenState,
	onAnswerChange: (UserAnswer) -> Unit,
	modifier: Modifier = Modifier
) {
	Column(modifier = modifier) {
		state.questions.forEach { question ->
			SimpleTestQuestion(
				question = question,
				answer = state.answers.first { (it.questionId == question.questionId) },
				onAnswerChange = onAnswerChange
			)
		}
	}
}
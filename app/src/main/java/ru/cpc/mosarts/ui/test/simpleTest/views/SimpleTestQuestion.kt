package ru.cpc.mosarts.ui.test.simpleTest.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.cpc.mosarts.domain.models.Question
import ru.cpc.mosarts.domain.models.QuestionType
import ru.cpc.mosarts.domain.models.UserAnswer
import ru.cpc.mosarts.ui.test.simpleTest.views.players.CustomMediaPlayer
import ru.cpc.mosarts.ui.views.Spacer

@Composable
fun SimpleTestQuestion(
	question: Question,
	answer: UserAnswer,
	player: CustomMediaPlayer,
	onAnswerChange: (UserAnswer) -> Unit,
	startPlayer: (String) -> Unit,
	modifier: Modifier = Modifier
) {
	Column(
		modifier = modifier
			.fillMaxWidth()
			.padding(10.dp)
	) {
		QuestionField(
			question = question,
			modifier = Modifier.align(CenterHorizontally),
			player = player,
			startPlayer = startPlayer
		)
		AnswerField(
			question = question,
			answer = answer,
			onAnswerChange = onAnswerChange,
			modifier = Modifier.align(CenterHorizontally),
			player = player,
			startPlayer = startPlayer
		)
	}
}


@Composable
fun QuestionField(
	question: Question,
	player: CustomMediaPlayer,
	startPlayer: (String) -> Unit,
	modifier: Modifier = Modifier
) {
	
	Box(modifier = modifier) {
		when (question.questionType) {
			QuestionType.TextText -> question.question.textQuestion?.let { TextQuestion(question = it) }
			QuestionType.TextOptions -> question.question.textQuestion?.let { TextQuestion(question = it) }
			QuestionType.ImageConnect -> {
				Column {
					question.question.textQuestion?.let { TextQuestion(question = it) }
					Spacer(size = 20.dp)
					question.question.source?.let {
						ImageQuestion(
							question = it
						)
					}
				}
			}
			
			QuestionType.VideoMusic -> {
				Column {
					question.question.textQuestion?.let { TextQuestion(question = it) }
					Spacer(size = 20.dp)
					question.question.source?.let {
						VideoQuestion(
							question = it,
							modifier = Modifier.height(150.dp)
						)
					}
				}
			}
			
			QuestionType.MusicImage -> {
				Column {
					question.question.textQuestion?.let { TextQuestion(question = it) }
					Spacer(size = 20.dp)
					question.question.source?.let {
						MusicQuestion(
							player = player,
							question = it,
							startPlayer = startPlayer
						)
					}
				}
			}
			
			QuestionType.TextDraw -> question.question.textQuestion?.let { TextQuestion(question = it) }
		}
	}
}

@Composable
fun AnswerField(
	question: Question,
	answer: UserAnswer,
	onAnswerChange: (UserAnswer) -> Unit,
	startPlayer: (String) -> Unit,
	player: CustomMediaPlayer,
	modifier: Modifier = Modifier,
) {
	Box(modifier = modifier) {
		when (question.questionType) {
			QuestionType.TextText -> TextAnswer(
				answer = answer,
				question = question,
				onAnswerChange = onAnswerChange
			)
			
			QuestionType.TextOptions ->
				OptionsAnswer(
					question = question,
					answer = answer,
					onAnswerChange = onAnswerChange
				)
			
			QuestionType.TextDraw -> {}
			QuestionType.MusicImage -> {
				ImageAnswer(
					question = question,
					answer = answer,
					onAnswerChange = onAnswerChange
				)
			}
			
			QuestionType.VideoMusic -> {
				MusicAnswer(
					player = player,
					question = question,
					answer = answer,
					onAnswerChange = onAnswerChange,
					startPlayer = startPlayer
				)
			}
			
			QuestionType.ImageConnect -> {}
		}
	}
}
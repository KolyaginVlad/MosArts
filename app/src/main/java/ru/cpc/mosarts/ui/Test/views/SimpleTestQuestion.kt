package ru.cpc.mosarts.ui.test.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.cpc.mosarts.domain.models.AnswerType
import ru.cpc.mosarts.domain.models.Question
import ru.cpc.mosarts.domain.models.UserAnswer

@Composable
fun SimpleTestQuestion(
	question: Question,
	answer: UserAnswer,
	onAnswerChange: (UserAnswer) -> Unit,
	modifier: Modifier = Modifier
) {
	Column(modifier = modifier) {
		QuestionField(question = question)
		AnswerField(question = question, answer = answer, onAnswerChange = onAnswerChange)
	}
}


@Composable
fun QuestionField(
	question: Question,
	modifier: Modifier = Modifier
) {
	Box(modifier = modifier) {
		question.question.textQuestion?.let { TextQuestion(question = it) }
		question.question.imageQuestion?.let { }
		question.question.musicQuestion?.let { }
	}
}

@Composable
fun AnswerField(
	question: Question,
	answer: UserAnswer,
	onAnswerChange: (UserAnswer) -> Unit,
	modifier: Modifier = Modifier
) {
	Box(modifier = modifier) {
		when (question.answerType) {
			AnswerType.Text ->
				TextAnswer(
					answer = answer,
					question = question,
					onAnswerChange = onAnswerChange
				)
			
			AnswerType.Drawing -> answer.drawingAnswer?.let { }
			AnswerType.Options -> OptionsAnswer(
				question = question,
				answer = answer,
				onAnswerChange = onAnswerChange
			)
			
		}
	}
}
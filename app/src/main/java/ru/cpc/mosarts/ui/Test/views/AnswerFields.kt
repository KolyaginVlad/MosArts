package ru.cpc.mosarts.ui.test.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.cpc.mosarts.domain.models.Question
import ru.cpc.mosarts.domain.models.UserAnswer


@Composable
fun TextAnswer(
	answer: UserAnswer,
	question: Question,
	onAnswerChange: (UserAnswer) -> Unit,
	modifier: Modifier = Modifier,
) {
	TextField(
		value = answer.textAnswer ?: "",
		modifier = modifier,
		onValueChange = {
			onAnswerChange(
				UserAnswer(
					textAnswer = it,
					questionId = question.questionId
				)
			)
		})
}


@Composable
fun OptionsAnswer(
	question: Question,
	answer: UserAnswer,
	onAnswerChange: (UserAnswer) -> Unit,
	modifier: Modifier = Modifier,
) {
	Column(modifier = modifier)
	{
		question.answerVariants.optionsAnswer?.forEach {
			Row {
				RadioButton(
					selected = answer.optionsAnswer?.contains(it) == true,
					onClick = {
						if (answer.optionsAnswer?.contains(it) != true) {
							answer.optionsAnswer?.add(it)
						} else answer.optionsAnswer.remove(it)
						onAnswerChange(
							answer
						)
					}
				)
				Text(text = it)
			}
		}
	}
}
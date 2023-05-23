package ru.cpc.mosarts.ui.test.views

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.Top
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import ru.cpc.mosarts.domain.models.AnswerVariant
import ru.cpc.mosarts.domain.models.Question
import ru.cpc.mosarts.domain.models.UserAnswer
import ru.cpc.mosarts.ui.test.views.players.Player
import ru.cpc.mosarts.ui.views.Spacer

/*
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
*/

@Composable
fun OptionsAnswer(
	question: Question,
	answer: UserAnswer,
	onAnswerChange: (UserAnswer) -> Unit,
	modifier: Modifier = Modifier,
) {
	Column(modifier = modifier)
	{
		question.answerVariants?.forEach {
			Row(
				modifier = Modifier
					.border(
						width = 5.dp,
						color = borderColor(answer = answer, question = question, it = it)
					)
					.width(200.dp)
			) {
				RadioButton(
					selected = answer.textAnswer == it.textVariant,
					onClick = {
						onAnswerChange(UserAnswer(it.textVariant))
					}
				)
				Text(text = it.textVariant)
			}
		}
	}
}

fun borderColor(answer: UserAnswer, question: Question, it: AnswerVariant) =
	if (checkAns(
			answer,
			question
		) && it.textVariant == answer.textAnswer
	) Color.Green
	else if (it.textVariant == answer.textAnswer) Color.Red
	else Color.White


fun checkAns(answer: UserAnswer, question: Question) = answer == question.rightAnswer

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ImageAnswer(
	question: Question,
	onAnswerChange: (UserAnswer) -> Unit,
	modifier: Modifier = Modifier,
	answer: UserAnswer,
) {
	Column(modifier = modifier)
	{
		question.answerVariants?.forEachIndexed { index, it ->
			Row(
				modifier = modifier
					.border(
						width = 5.dp,
						color = borderColor(answer = answer, question = question, it = it)
					)
			) {
				RadioButton(
					selected = answer.textAnswer == it.textVariant,
					onClick = {
						onAnswerChange(UserAnswer(it.textVariant))
					},
					modifier = Modifier.align(CenterVertically)
				
				)
				Column(modifier = Modifier.align(Top)) {
					GlideImage(
						model = it.source,
						contentDescription = null,
						modifier = Modifier
							.fillMaxSize(0.3f)
							.height(500.dp)
							.padding(0.dp),
					)
					Text(text = it.textVariant)
				}
			}
			Spacer(40.dp)
			
		}
	}
}

@Composable
fun MusicAnswer(
	question: Question,
	answer: UserAnswer,
	onAnswerChange: (UserAnswer) -> Unit,
	modifier: Modifier = Modifier,
	player: MediaPlayer
) {
	Column(modifier = modifier)
	{
		question.answerVariants?.forEach {
			val mediaPlayer = player
			val context = LocalContext.current
			val url = it.source
			mediaPlayer.reset()
			mediaPlayer.setAudioAttributes(
				AudioAttributes.Builder()
					.setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
					.build()
			)
			
			mediaPlayer.setDataSource(context, Uri.parse(url))
			mediaPlayer.prepareAsync()
			Row(
				modifier = modifier
					.border(
						width = 5.dp,
						color = borderColor(answer = answer, question = question, it = it)
					)
			) {
				RadioButton(
					selected = answer.textAnswer == it.textVariant,
					onClick = {
						onAnswerChange(UserAnswer(it.textVariant))
					}
				)
				Player(
					mediaPlayer,
					modifier = modifier.width(200.dp)
				)
			}
		}
	}
}
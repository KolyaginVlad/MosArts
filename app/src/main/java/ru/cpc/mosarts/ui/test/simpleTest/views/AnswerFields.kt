package ru.cpc.mosarts.ui.test.simpleTest.views

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import ru.cpc.mosarts.domain.models.AnswerVariant
import ru.cpc.mosarts.domain.models.Question
import ru.cpc.mosarts.domain.models.UserAnswer
import ru.cpc.mosarts.ui.test.simpleTest.views.players.CustomMediaPlayer
import ru.cpc.mosarts.ui.test.simpleTest.views.players.Player
import ru.cpc.mosarts.ui.theme.Black
import ru.cpc.mosarts.ui.views.Spacer


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
        question.answerVariants?.forEach {
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .border(
                        width = 5.dp,
                        color = borderColor(answer = answer, question = question, it = it),
                        shape = RoundedCornerShape(20)
                    )
                    .width(200.dp)
            ) {
                RadioButton(
                    selected = answer.textAnswer == it.textVariant,
                    onClick = remember(it.textVariant, onAnswerChange) {
                        {
                            onAnswerChange(UserAnswer(it.textVariant))
                        }
                    },
                    modifier = Modifier.align(CenterVertically)
                )
                Text(
                    text = it.textVariant,
                    modifier = Modifier.align(CenterVertically),
                    style = MaterialTheme.typography.body2,
                    color = Black
                )
            }
        }
    }
}

fun borderColor(answer: UserAnswer, question: Question, it: AnswerVariant) =
    if (it.textVariant == question.rightAnswer.textAnswer && answer.textAnswer != null) Color.Green
    else if (it.textVariant == answer.textAnswer) Color.Red
    else Color.White

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
                    .padding(10.dp)
                    .border(
                        width = 5.dp,
                        color = borderColor(answer = answer, question = question, it = it),
                        shape = RoundedCornerShape(20)

                    )
            ) {
                RadioButton(
                    selected = answer.textAnswer == it.textVariant,
                    onClick = remember(it.textVariant, onAnswerChange) {
                        {
                            onAnswerChange(UserAnswer(it.textVariant))
                        }
                    },
                    modifier = Modifier.align(CenterVertically)

                )
                Column(modifier = Modifier.padding(10.dp)) {
                    GlideImage(
                        model = it.source,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize(0.3f)
                            .height(50.dp)
                            .padding(0.dp),
                    )
                    Text(text = it.textVariant, style = MaterialTheme.typography.body2)
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
    player: CustomMediaPlayer,
    startPlayer: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier)
    {
        question.answerVariants?.forEach {
            Row(
                modifier = modifier
                    .padding(10.dp)
                    .border(
                        width = 5.dp,
                        color = borderColor(answer = answer, question = question, it = it),
                        shape = RoundedCornerShape(20)
                    )
            ) {
                RadioButton(
                    selected = answer.textAnswer == it.textVariant,
                    onClick = {
                        onAnswerChange(UserAnswer(it.textVariant))
                    }
                )
                it.source?.let { it1 ->
                    Player(
                        player,
                        modifier = modifier.width(200.dp),
                        url = it1,
                        startPlayer = startPlayer
                    )
                }
            }
        }
    }
}
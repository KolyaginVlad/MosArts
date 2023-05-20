package ru.cpc.mosarts.ui.test.views

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TextQuestion(
	question: String,
	modifier: Modifier = Modifier,
) {
	Text(text = question, modifier = modifier)
}
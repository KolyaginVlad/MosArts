package ru.cpc.mosarts.ui.test.simpleTest.views

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import ru.cpc.mosarts.domain.models.Difficulty
import ru.cpc.mosarts.ui.theme.MosArtsTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ExplainDialog(
	text: String,
	openDialogCustom: MutableState<Boolean>,
	modifier: Modifier = Modifier,
) {
	Dialog(
		onDismissRequest = { openDialogCustom.value = false },
		properties = DialogProperties(usePlatformDefaultWidth = false),
	) {
		Surface(
			color = Color.Transparent,
			modifier = modifier.fillMaxWidth(0.95F)
		) {
			
			ExplainDialogUi(
				openDialogCustom = openDialogCustom,
				text = text,
			)
		}
	}
}

@Composable
fun ExplainDialogUi(
	openDialogCustom: MutableState<Boolean>,
	text: String,
) {
	val difficulty = remember {
		mutableStateOf(Difficulty.Easy)
	}
	Card(
		shape = RoundedCornerShape(40.dp),
		modifier = Modifier.height(200.dp),
	) {
		Column(
			Modifier
				.background(MaterialTheme.colors.onPrimary)
				.padding(start = 12.dp, end = 12.dp, top = 32.dp, bottom = 12.dp)
				.fillMaxWidth()
		)
		{
			Text(text = text, style = MaterialTheme.typography.body1)
		}
	}
}

@SuppressLint("UnrememberedMutableState")
@Preview(locale = "ru")
@Composable
private fun SalaryDialogPrev() {
	MosArtsTheme {
		ExplainDialog(
			modifier = Modifier,
			openDialogCustom = mutableStateOf(true),
			text = "{ _ -> }"
		)
	}
}


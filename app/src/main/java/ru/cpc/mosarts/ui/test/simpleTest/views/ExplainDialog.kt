package ru.cpc.mosarts.ui.test.simpleTest.views

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import ru.cpc.mosarts.ui.theme.MosArtsTheme

@Composable
fun ExplainDialog(
	text: String,
	onDismissRequest: () -> Unit,
	modifier: Modifier = Modifier,
) {
	Dialog(
		onDismissRequest = onDismissRequest,
		properties = DialogProperties(usePlatformDefaultWidth = false),
	) {
		Surface(
			color = Color.Transparent,
			modifier = modifier.fillMaxWidth(0.95F)
		) {
			
			ExplainDialogUi(
				text = text,
			)
		}
	}
}

@Composable
fun ExplainDialogUi(
	text: String,
) {
	Card(
		shape = RoundedCornerShape(24.dp),
	) {
		Column(
			Modifier
				.background(MaterialTheme.colors.onPrimary)
				.padding(32.dp)
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
			onDismissRequest = {},
			text = "{ _ -> }"
		)
	}
}


package ru.cpc.mosarts.ui.test.testSelect.views

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import ru.cpc.mosarts.R
import ru.cpc.mosarts.domain.models.Difficulty
import ru.cpc.mosarts.ui.test.utils.toStringId
import ru.cpc.mosarts.ui.theme.MosArtsTheme
import java.util.Locale

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DifficultyDialog(
	onSubmit: (Difficulty) -> Unit,
	openDialogCustom: MutableState<Boolean>,
	modifier: Modifier = Modifier,
) {
	Dialog(
		onDismissRequest = { openDialogCustom.value = false },
		properties = DialogProperties(usePlatformDefaultWidth = true),
	) {
		Surface(
			color = Color.Transparent,
			modifier = modifier.fillMaxWidth(0.95F)
		) {
			
			DifficultyDialogUi(
				openDialogCustom = openDialogCustom,
				onSubmit = onSubmit,
			)
		}
	}
}

@Composable
fun DifficultyDialogUi(
	openDialogCustom: MutableState<Boolean>,
	onSubmit: (Difficulty) -> Unit,
) {
	val difficulty = remember {
		mutableStateOf(Difficulty.Easy)
	}
	Card(
		shape = RoundedCornerShape(40.dp),
		modifier = Modifier,
	) {
		Column(
			Modifier
				.background(MaterialTheme.colors.onPrimary)
				.padding(start = 12.dp, end = 12.dp, top = 32.dp, bottom = 12.dp)
				.fillMaxWidth()
		)
		{
			Difficulty.values().forEach {
				Row(
					modifier = Modifier
						.padding(10.dp)
						.width(200.dp)
				) {
					RadioButton(
						selected = difficulty.value == it,
						onClick = remember(it) {
							{
								difficulty.value = it
							}
						},
						modifier = Modifier.align(Alignment.CenterVertically)
					)
					Text(
						text = stringResource(id = it.toStringId()),
						modifier = Modifier.align(Alignment.CenterVertically),
						style = MaterialTheme.typography.body2
					)
				}
			}
			Row(
				modifier = Modifier
					.padding(top = 32.dp)
					.fillMaxWidth(),
				horizontalArrangement = Arrangement.SpaceBetween
			) {
				Button(
					onClick = {
						onSubmit(difficulty.value)
						openDialogCustom.value = !openDialogCustom.value
					},
					modifier = Modifier,
					colors = ButtonDefaults.buttonColors(
						backgroundColor = MaterialTheme.colors.primaryVariant,
						contentColor = MaterialTheme.colors.onPrimary
					)
				) {
					Text(
						modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp),
						text = stringResource(id = R.string.submit).uppercase(Locale.ROOT)
					)
				}
				Button(
					onClick = {
						openDialogCustom.value = !openDialogCustom.value
					},
					modifier = Modifier,
					colors = ButtonDefaults.buttonColors(
						backgroundColor = MaterialTheme.colors.primaryVariant,
						contentColor = MaterialTheme.colors.onPrimary
					)
				) {
					Text(
						modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp),
						text = stringResource(id = R.string.cancel).uppercase(Locale.ROOT)
					)
				}
			}
		}
	}
}

@SuppressLint("UnrememberedMutableState")
@Preview(locale = "ru")
@Composable
private fun SalaryDialogPrev() {
	MosArtsTheme {
		DifficultyDialog(
			modifier = Modifier,
			openDialogCustom = mutableStateOf(true),
			onSubmit = { _ -> })
	}
}


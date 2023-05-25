package ru.cpc.mosarts.ui.test.views

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.cpc.mosarts.R
import ru.cpc.mosarts.ui.views.Spacer

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Results(
	points: Int
) {
	Column() {
		Text(
			text = stringResource(id = R.string.test_is_finished),
			style = MaterialTheme.typography.h3
		)
		Spacer(size = 25.dp)
		Text(
			text = stringResource(id = R.string.test_points) + " " + points.toString() + " " + pluralStringResource(
				R.plurals.points,
				points
			),
			style = MaterialTheme.typography.h4
		)
	}
}
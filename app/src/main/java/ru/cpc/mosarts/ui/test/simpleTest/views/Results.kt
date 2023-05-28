package ru.cpc.mosarts.ui.test.simpleTest.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.cpc.mosarts.R
import ru.cpc.mosarts.ui.theme.Black
import ru.cpc.mosarts.ui.views.Spacer

@Composable
fun Results(
    points: Int,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.test_is_finished),
            style = MaterialTheme.typography.h3,
            color = Black,
            textAlign = TextAlign.Center
        )
        Spacer(size = 25.dp)
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.test_points) + " " + points.toString() + " " + pluralStringResource(
                R.plurals.points,
                points
            ),
            style = MaterialTheme.typography.h4,
            color = Black,
            textAlign = TextAlign.Center
        )
    }
}
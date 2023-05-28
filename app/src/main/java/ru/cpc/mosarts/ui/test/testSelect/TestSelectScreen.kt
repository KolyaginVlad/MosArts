package ru.cpc.mosarts.ui.test.testSelect

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import ru.cpc.mosarts.domain.models.NamesOfTest
import ru.cpc.mosarts.ui.activities.utils.MainNavGraph
import ru.cpc.mosarts.ui.destinations.SimpleTestScreenDestination
import ru.cpc.mosarts.ui.test.testSelect.views.DifficultyDialog
import ru.cpc.mosarts.ui.test.utils.toStringId

@MainNavGraph
@Destination
@Composable
fun TestSelectScreen(
	navigator: DestinationsNavigator,
	viewModel: TestSelectViewModel = hiltViewModel()
) {
	val openDifficultyDialog = remember { mutableStateOf(false) }
	val state by viewModel.screenState.collectAsStateWithLifecycle()
	val context = LocalContext.current
	LaunchedEffect(Unit) {
		
		viewModel.event.collect {
			when (it) {
				is TestSelectScreenEvent.Error -> Toast.makeText(
					context, it.text, Toast.LENGTH_LONG
				).show()
				
				is TestSelectScreenEvent.GoToTest -> navigator.navigate(
					SimpleTestScreenDestination(it.test, it.difficulty)
				)
				
				is TestSelectScreenEvent.TestSelected -> {
					openDifficultyDialog.value = true
				}
			}
		}
	}
	if (openDifficultyDialog.value) {
		DifficultyDialog(
			onSubmit = viewModel::goToTest,
			openDialogCustom = openDifficultyDialog
		)
	}
	TestSelectScreenContent(
		state = state,
		viewModel::onVariantClick
	)
}

@Composable
fun TestSelectScreenContent(
	state: TestSelectScreenState,
	onVariantClick: (NamesOfTest) -> Unit
) {
	val scrollState = rememberScrollState()
	
	Column(modifier = Modifier.verticalScroll(scrollState)) {
		NamesOfTest.values().forEach {
			VariantOfTest(
				namesOfTest = it,
				modifier = Modifier
					.fillMaxWidth()
					.padding(20.dp),
				onVariantClick = onVariantClick
			)
		}
	}
}


@Composable
fun VariantOfTest(
	namesOfTest: NamesOfTest,
	modifier: Modifier,
	onVariantClick: (NamesOfTest) -> Unit
) {
	Card(
		modifier = modifier.clickable(
			onClick = remember(namesOfTest) { { onVariantClick(namesOfTest) } }
		),
		elevation = 10.dp
	) {
		Text(
			text = stringResource(id = namesOfTest.toStringId()),
			modifier = Modifier.padding(40.dp),
			style = MaterialTheme.typography.h4,
			textAlign = TextAlign.Center
		)
	}
}
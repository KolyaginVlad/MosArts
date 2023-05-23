package ru.cpc.mosarts.ui.schoolmap

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.yandex.mapkit.map.MapObjectTapListener
import ru.cpc.mosarts.R
import ru.cpc.mosarts.ui.views.Map

@Destination
@Composable
fun SchoolMapScreen(
    viewModel: SchoolMapViewModel = hiltViewModel(),
) {
    val state by viewModel.screenState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when (it) {
                else -> {}
            }
        }
    }
    Content(
        state = state,
        onSchoolTap = viewModel,
        onDismiss = viewModel::onDismiss,
        onFilterChange = viewModel::onFilterChange
    )
}

@Composable
private fun Content(
    state: SchoolMapScreenState,
    onSchoolTap: MapObjectTapListener,
    onDismiss: () -> Unit,
    onFilterChange: (String) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Map(
            listOfSchool = state.listOfSchools,
            modifier = Modifier.fillMaxSize(),
            onSchoolTap = onSchoolTap
        )
        state.alertDialogInfo?.let {
            AlertDialog(
                onDismissRequest = onDismiss,
                buttons = {
                    Button(modifier = Modifier.fillMaxWidth().padding(8.dp), onClick = onDismiss) {
                        Text(text = stringResource(R.string.ok))
                    }
                },
                title = {
                    Text(text = it.name, style = MaterialTheme.typography.h6)
                },
                text = {
                    Column {
                        Text(text = it.description, style = MaterialTheme.typography.subtitle1)
                        Text(text = it.email, style = MaterialTheme.typography.subtitle2)
                        Text(text = it.phone, style = MaterialTheme.typography.subtitle2)
                        Text(text = it.address, style = MaterialTheme.typography.subtitle2)
                        Text(
                            text = it.themes.joinToString { it.name }, style = MaterialTheme
                                .typography.subtitle2
                        )
                    }
                }
            )
        }
    }
}
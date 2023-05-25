package ru.cpc.mosarts.ui.themes.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.PersistentList
import ru.cpc.mosarts.ui.themes.models.ThemeUiData

@Composable
fun ThemesCheckBoxGroup(
    listOfThemes: PersistentList<ThemeUiData>,
    modifier: Modifier = Modifier,
    onThemeSelected: (ThemeUiData, Boolean) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        listOfThemes.forEach { data ->
            ThemeCheckBoxItem(
                modifier = Modifier,
                data = data,
                onCheckedChange = remember(data) {
                    {
                        onThemeSelected(data, it)
                    }
                }
            )
        }
    }
}

@Composable
private fun ThemeCheckBoxItem(
    modifier: Modifier,
    data: ThemeUiData,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = data.isSelected, onCheckedChange = onCheckedChange)
        Text(text = data.name)
    }
}
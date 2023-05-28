package ru.cpc.mosarts.ui.views

import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.cpc.mosarts.ui.theme.Black

@Composable
fun FormTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = label,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Black
        ),
        isError = isError,
        singleLine = true
    )
}
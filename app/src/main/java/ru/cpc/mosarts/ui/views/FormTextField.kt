package ru.cpc.mosarts.ui.views

import androidx.compose.material.TextField
import androidx.compose.runtime.Composable

@Composable
fun FormTextField(
	value: String,
	onValueChange: (String) -> Unit,
	label: @Composable() (() -> Unit)
) {
	TextField(value = value, onValueChange = onValueChange, label = label)
}
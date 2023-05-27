package ru.cpc.mosarts.ui.test.utils

import androidx.annotation.StringRes
import ru.cpc.mosarts.R
import ru.cpc.mosarts.domain.models.Difficulty
import ru.cpc.mosarts.domain.models.NamesOfTest


@StringRes
fun NamesOfTest.toStringId():Int = when (this){
	NamesOfTest.TextOptions -> R.string.text_options
	NamesOfTest.MusicImage -> R.string.music_image
}

@StringRes
fun Difficulty.toStringId():Int = when (this){
	Difficulty.Easy -> R.string.easy
	Difficulty.Medium -> R.string.medium
	Difficulty.Hard -> R.string.hard
}
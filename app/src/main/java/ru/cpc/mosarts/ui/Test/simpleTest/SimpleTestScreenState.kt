package ru.cpc.mosarts.ui.test.simpleTest

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import ru.cpc.mosarts.domain.models.Question
import ru.cpc.mosarts.domain.models.UserAnswer
import ru.cpc.mosarts.utils.base.State


data class SimpleTestScreenState (
	val isLoading:Boolean = true,
	val questions: PersistentList<Question> = persistentListOf(),
	val answers: SnapshotStateList<UserAnswer> =  mutableStateListOf()
): State()

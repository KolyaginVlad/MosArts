package ru.cpc.mosarts.ui.test.simpleTest

import android.media.MediaPlayer
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import ru.cpc.mosarts.domain.models.Question
import ru.cpc.mosarts.domain.models.TestResults
import ru.cpc.mosarts.domain.models.UserAnswer
import ru.cpc.mosarts.ui.test.views.players.CustomMediaPlayer
import ru.cpc.mosarts.utils.base.State


data class SimpleTestScreenState(
	val isLoading: Boolean = true,
	val questions: PersistentList<Question> = persistentListOf(),
	val answers: SnapshotStateList<UserAnswer> = mutableStateListOf(),
	val currentQuestion: Int? = null,
	val audioPlayer: CustomMediaPlayer = CustomMediaPlayer(),
	val results: TestResults = TestResults(),
	val finished: Boolean = false
) : State()

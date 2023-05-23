package ru.cpc.mosarts.ui.test.views

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import ru.cpc.mosarts.ui.test.views.players.Player
import ru.cpc.mosarts.ui.test.views.players.VideoPlayer

@Composable
fun TextQuestion(
	question: String,
	modifier: Modifier = Modifier,
) {
	Text(text = question, modifier = modifier)
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ImageQuestion(
	question: String,
	modifier: Modifier = Modifier,
) {
	GlideImage(model = question, contentDescription = null, modifier = modifier)
}

@Composable
fun MusicQuestion(
	question: String,
	modifier: Modifier = Modifier,
	player: MediaPlayer
	) {
	val mediaPlayer = player
	mediaPlayer.reset()
	val context = LocalContext.current
	val url = question
	mediaPlayer.setAudioAttributes(
		AudioAttributes.Builder()
			.setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
			.build()
	)
	mediaPlayer.setDataSource(context, Uri.parse(url))
	mediaPlayer.prepareAsync()
	Player(
		mediaPlayer,
		modifier = modifier.width(200.dp)
	)
}

@Composable
fun VideoQuestion(
	question: String,
	modifier: Modifier = Modifier,
) {
	VideoPlayer(
		question,
		modifier
	)
}




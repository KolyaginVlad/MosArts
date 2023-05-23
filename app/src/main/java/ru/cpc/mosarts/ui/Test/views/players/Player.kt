package ru.cpc.mosarts.ui.test.views.players

import android.media.MediaPlayer
import android.os.CountDownTimer
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.PlayArrow
import androidx.compose.material.icons.twotone.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Player(
	player: MediaPlayer?,
	modifier: Modifier = Modifier
) {
	val playing = remember {
		mutableStateOf(false)
	}
	val position = remember {
		mutableStateOf(0F)
	}
	if (player != null) {
		Row(
			modifier = modifier
		) {
			Icon(
				imageVector = if (!playing.value || player.currentPosition == player.duration) Icons.TwoTone.PlayArrow else Icons.TwoTone.Refresh,
				contentDescription = "image",
				tint = MaterialTheme.colors.primaryVariant, modifier = Modifier
					.align(CenterVertically)
					.size(20.dp)
					.clickable(onClick = {
						if (player.isPlaying) {
							player.pause()
							playing.value = false
						} else {
							player.start()
							playing.value = true
						}
						
						object : CountDownTimer(player.duration.toLong(), 100) {
							
							override fun onTick(millisUntilFinished: Long) {
								position.value = player.currentPosition.toFloat()
								if (player.currentPosition == player.duration) {
									playing.value = false
								}
							}
							
							override fun onFinish() {
							
							}
						}.start()
					})
			)
			Slider(
				value = position.value,
				valueRange = 0F..player.duration.toFloat(),
				onValueChange = {
					position.value = it
					player.seekTo(it.toInt())
				}
			)
		}
	}
}

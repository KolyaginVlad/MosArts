package ru.cpc.mosarts.ui.test.simpleTest.views.players

import android.os.CountDownTimer
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.cpc.mosarts.R

@Composable
fun Player(
    player: CustomMediaPlayer,
    startPlayer: (String) -> Unit,
    url: String,
    modifier: Modifier = Modifier,
) {

    val playing = remember {
        mutableStateOf(false)
    }
    val position = remember {
        mutableStateOf(0F)
    }
    val duration = remember {
        mutableStateOf(0F)
    }
    LaunchedEffect(player, playing.value) {
        player.setOnPreparedListener {
            duration.value = player.duration.toFloat()
            player.start()
        }
    }
    Row(
        modifier = modifier
    ) {
        Icon(
            painter = if (!playing.value) painterResource(
                id = R.drawable.play_button
            ) else painterResource(
                id = R.drawable.circle_pause
            ),
            contentDescription = "image",
            tint = MaterialTheme.colors.primaryVariant, modifier = Modifier
                .align(CenterVertically)
                .size(20.dp)
                .clickable {
                    if (player.isPlaying) {
                        playing.value = false
                        player.pause()
                    } else {
                        playing.value = true
                        startPlayer(url)
                    }

                    object : CountDownTimer(player.duration.toLong(), 100) {

                        override fun onTick(millisUntilFinished: Long) {
                            if (player.getDataSource() == url) position.value =
                                player.currentPosition.toFloat()
                        }

                        override fun onFinish() {

                        }
                    }.start()
                }
        )
        Slider(
            value = position.value,
            valueRange = 0F..duration.value,
            onValueChange = {
                if (player.getDataSource() == url) {
                    position.value = it
                    player.seekTo(it.toInt())
                }

            }
        )
    }
}

package ru.cpc.mosarts.ui.test.simpleTest.views

import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import ru.cpc.mosarts.ui.test.simpleTest.views.players.CustomMediaPlayer
import ru.cpc.mosarts.ui.test.simpleTest.views.players.Player
import ru.cpc.mosarts.ui.test.simpleTest.views.players.VideoPlayer
import ru.cpc.mosarts.ui.theme.Black

@Composable
fun TextQuestion(
    question: String,
    modifier: Modifier = Modifier,
) {
    Text(text = question, modifier = modifier, color = Black)
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
    player: CustomMediaPlayer,
    startPlayer: (String) -> Unit,
) {
    Player(
        player,
        modifier = modifier.width(200.dp),
        url = question,
        startPlayer = startPlayer
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




package ru.cpc.mosarts.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import ru.cpc.mosarts.ui.theme.MosArtsTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.vk.api.sdk.VK
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.AndroidEntryPoint
import ru.cpc.mosarts.ui.auth.Synchronizer

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val login = VK.login(this) {
        Synchronizer.sendResult(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.initialize(this)
        setContent {
            LaunchedEffect(Unit) {
                Synchronizer.login.collect {
                    login.launch(it)
                }
            }
            MosArtsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    DestinationsNavHost(
                        modifier = Modifier
                            .fillMaxSize(),
                        navGraph = NavGraphs.root
                    )
                }
            }
        }
    }
}
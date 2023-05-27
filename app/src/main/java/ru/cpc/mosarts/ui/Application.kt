package ru.cpc.mosarts.ui

import com.vk.api.sdk.VK
import com.vk.api.sdk.VKTokenExpiredHandler
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp
import ru.cpc.mosarts.domain.repositories.VkRepository
import ru.cpc.mosarts.domain.usecases.SaveTokenUseCase
import javax.inject.Inject

@HiltAndroidApp
class Application : android.app.Application(){

    @Inject
    lateinit var vkRepository: VkRepository

    override fun onCreate() {
        super.onCreate()
        VK.addTokenExpiredHandler(tokenTracker)
        MapKitFactory.setApiKey("ec19dc8f-cd59-4851-8b56-f7774c42ed1c")
    }

    private val tokenTracker = object: VKTokenExpiredHandler {
        override fun onTokenExpired() {
            vkRepository.vkToken = null
        }
    }
}
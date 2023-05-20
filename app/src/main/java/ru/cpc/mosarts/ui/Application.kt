package ru.cpc.mosarts.ui

import com.vk.api.sdk.VK
import com.vk.api.sdk.VKTokenExpiredHandler
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
    }

    private val tokenTracker = object: VKTokenExpiredHandler {
        override fun onTokenExpired() {
            vkRepository.vkToken = null
        }
    }
}
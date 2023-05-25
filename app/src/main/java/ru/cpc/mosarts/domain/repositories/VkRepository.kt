package ru.cpc.mosarts.domain.repositories

import ru.cpc.mosarts.domain.models.ProfileInfo

interface VkRepository {

    var vkToken: String?

    suspend fun getProfileInfo(): ProfileInfo
}
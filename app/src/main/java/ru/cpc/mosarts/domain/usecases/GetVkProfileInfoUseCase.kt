package ru.cpc.mosarts.domain.usecases

import com.osinit.mycompany.domain.usecases.common.NoArgsUseCase
import ru.cpc.mosarts.domain.models.ProfileInfo
import ru.cpc.mosarts.domain.repositories.VkRepository
import javax.inject.Inject

abstract class GetVkProfileInfoUseCase : NoArgsUseCase<ProfileInfo>()

class GetVkProfileInfoUseCaseImpl @Inject constructor(
    private val vkRepository: VkRepository,
) : GetVkProfileInfoUseCase() {
    override suspend fun run(): Result<ProfileInfo> = runCatching {
        vkRepository.getProfileInfo()
    }
}
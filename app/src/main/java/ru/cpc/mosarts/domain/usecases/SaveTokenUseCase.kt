package ru.cpc.mosarts.domain.usecases

import com.osinit.mycompany.domain.usecases.common.UseCase
import ru.cpc.mosarts.domain.repositories.VkRepository
import javax.inject.Inject

abstract class SaveTokenUseCase : UseCase<Unit, String>()

class SaveTokenUseCaseImpl @Inject constructor(
    private val vkRepository: VkRepository,
) : SaveTokenUseCase() {
    override suspend fun run(args: String): Result<Unit> = runCatching {
        vkRepository.vkToken = args
    }
}
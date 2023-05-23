package ru.cpc.mosarts.domain.usecases

import com.osinit.mycompany.domain.usecases.common.UseCase
import ru.cpc.mosarts.domain.models.ThemeData
import ru.cpc.mosarts.domain.repositories.TestRepository
import javax.inject.Inject

abstract class SendSelectedThemesUseCase: UseCase<Unit, List<ThemeData>>()

class SendSelectedThemesUseCaseImpl @Inject constructor(
    private val testRepository: TestRepository
): SendSelectedThemesUseCase() {
    override suspend fun run(args: List<ThemeData>): Result<Unit> =
        testRepository.sendSelectedThemes(args)
}
package ru.cpc.mosarts.domain.usecases

import com.osinit.mycompany.domain.usecases.common.NoArgsUseCase
import com.osinit.mycompany.domain.usecases.common.UseCase
import ru.cpc.mosarts.domain.models.ThemeData
import ru.cpc.mosarts.domain.repositories.TestRepository
import javax.inject.Inject

abstract class GetThemesUseCase: NoArgsUseCase<List<ThemeData>>()

class GetThemesUseCaseImpl @Inject constructor(
    private val testRepository: TestRepository
): GetThemesUseCase() {
    override suspend fun run(): Result<List<ThemeData>> =
        testRepository.getThemes()
}
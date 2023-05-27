package ru.cpc.mosarts.domain.usecases

import com.osinit.mycompany.domain.usecases.common.NoArgsUseCase
import ru.cpc.mosarts.domain.models.School
import ru.cpc.mosarts.domain.repositories.SchoolRepository
import javax.inject.Inject

abstract class GetListOfSchoolsUseCase : NoArgsUseCase<List<School>>()

class GetListOfSchoolsUseCaseImpl @Inject constructor(
    private val schoolRepository: SchoolRepository,
) : GetListOfSchoolsUseCase() {

    override suspend fun run(): Result<List<School>> =
        schoolRepository.getSchools()

}
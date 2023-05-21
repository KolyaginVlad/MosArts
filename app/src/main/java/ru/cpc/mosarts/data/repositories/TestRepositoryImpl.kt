package ru.cpc.mosarts.data.repositories

import kotlinx.coroutines.delay
import ru.cpc.mosarts.domain.models.ThemeData
import ru.cpc.mosarts.domain.repositories.TestRepository
import javax.inject.Inject

class TestRepositoryImpl @Inject constructor(

) : TestRepository {

    override suspend fun getThemes(): Result<List<ThemeData>> {
        delay(300)
        return Result.success(
            listOf(
                ThemeData(
                    0,
                    "Музыка"
                ),
                ThemeData(
                    1,
                    "Хореография"
                ),
                ThemeData(
                    2,
                    "Изобразительное искусство"
                ),
                ThemeData(
                    3,
                    "Театр"
                ),
            )
        )
    }

    override suspend fun sendSelectedThemes(themes: List<ThemeData>): Result<Unit> {
        delay(100)
        return Result.success(Unit)
    }
}
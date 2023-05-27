package ru.cpc.mosarts.data.repositories

import kotlinx.collections.immutable.persistentListOf
import ru.cpc.mosarts.domain.models.School
import ru.cpc.mosarts.domain.models.ThemeData
import ru.cpc.mosarts.domain.repositories.SchoolRepository
import javax.inject.Inject

class SchoolRepositoryImpl @Inject constructor(

) : SchoolRepository {
    override suspend fun getSchools(): Result<List<School>> = runCatching {
        val themes = listOf(
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
        listOf(
            School(
                id = 0,
                name = "Детская музыкальная школа имени В.И.Мурадели",
                latitude = 55.740624,
                longitude = 37.590686,
                address = "Ул. Пречистенка, д. 32/1, стр. 1",
                description = "Best of the best",
                email = "muradeli@culture.mos.ru",
                phone = "+7 (495) 637-37-83",
                themes = themes
            ),
            School(
                id = 1,
                name = "Московская городская детская музыкальная школа имени С.С.Прокофьева",
                latitude = 55.766385,
                longitude = 37.669208,
                address = "Токмаков пер., д. 8",
                description = "Best of the best",
                email = "dmshprokofiev@culture.mos.ru",
                phone = "+7 (499) 261-03-83",
                themes = themes.subList(1, 3)
            ),
            School(
                id = 2,
                name = "Детская музыкальная школа имени В.В.Стасова",
                latitude = 55.726046,
                longitude = 37.632592,
                address = "Токмаков пер., д. 8",
                description = "Best of the best",
                email = "dmshprokofiev@culture.mos.ru",
                phone = "+7 (499) 261-03-83",
                themes = themes.subList(2, 3)
            ),
            School(
                id = 3,
                name = " Детская музыкальная школа имени Людвига ван Бетховена",
                latitude = 55.743887,
                longitude = 37.589734,
                address = "Ул. Пречистенка, д. 32/1, стр. 1",
                description = "Best of the best",
                email = "muradeli@culture.mos.ru",
                phone = "+7 (495) 637-37-83",
                themes = themes.subList(1, 2)
            ),
        )
    }
}
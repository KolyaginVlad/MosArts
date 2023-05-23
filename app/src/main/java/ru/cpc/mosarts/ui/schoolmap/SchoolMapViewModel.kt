package ru.cpc.mosarts.ui.schoolmap

import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.MapObject
import com.yandex.mapkit.map.MapObjectTapListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import okhttp3.internal.immutableListOf
import ru.cpc.mosarts.domain.models.School
import ru.cpc.mosarts.domain.models.ThemeData
import ru.cpc.mosarts.ui.models.SchoolMapInfo
import ru.cpc.mosarts.ui.models.mapToUi
import ru.cpc.mosarts.utils.base.BaseViewModel
import javax.inject.Inject
import kotlin.math.pow
import kotlin.math.sqrt

@HiltViewModel
class SchoolMapViewModel @Inject constructor(

) : BaseViewModel<SchoolMapScreenState, SchoolMapScreenEvent>(SchoolMapScreenState()),
    MapObjectTapListener {

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
    private var listOfSchools = persistentListOf(
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

    init {
        launchViewModelScope {
            updateState {
                it.copy(
                    listOfSchools = listOfSchools.map { it.mapToUi() }.toImmutableList()
                )
            }
        }
    }

    fun onSchoolTap(schoolMapInfo: SchoolMapInfo) {

    }

    fun onFilterChange(filter: String) {
        val newList = listOfSchools.filter { school ->
            school.themes.any { it.name.contains(filter) }
        }.map { it.mapToUi() }.toImmutableList()
        updateState { state ->
            state.copy(
                listOfSchools = newList
            )
        }
    }

    fun onDismiss() {
        updateState {
            it.copy(
                alertDialogInfo = null
            )
        }
    }

    override fun onMapObjectTap(p0: MapObject, point: Point): Boolean {
        currentState.listOfSchools.minBy {
            sqrt(
                (it.latitude - point.latitude).pow(2.0) + (it.longitude - point.longitude).pow(
                    2.0
                )
            )
        }.let { selected ->
            val info = listOfSchools.first { it.id == selected.id }
            updateState {
                it.copy(
                    alertDialogInfo = info
                )
            }
        }
        return true
    }
}
package ru.cpc.mosarts.ui.schoolmap

import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.MapObject
import com.yandex.mapkit.map.MapObjectTapListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import ru.cpc.mosarts.domain.models.School
import ru.cpc.mosarts.domain.usecases.GetListOfSchoolsUseCase
import ru.cpc.mosarts.ui.models.mapToUi
import ru.cpc.mosarts.utils.base.BaseViewModel
import javax.inject.Inject
import kotlin.math.pow
import kotlin.math.sqrt

@HiltViewModel
class SchoolMapViewModel @Inject constructor(
    getListOfSchoolsUseCase: GetListOfSchoolsUseCase,
) : BaseViewModel<SchoolMapScreenState, SchoolMapScreenEvent>(SchoolMapScreenState()),
    MapObjectTapListener {

    private var listOfSchools: List<School>? = null

    init {
        launchViewModelScope {
            listOfSchools = (getListOfSchoolsUseCase().getOrNull() ?: emptyList())
            val list = listOfSchools!!.map { it.mapToUi() }.toImmutableList()
            updateState {
                it.copy(
                    listOfSchools = list
                )
            }
        }
    }

    fun onFilterChange(filter: String) {
        updateState {
            it.copy(filter = filter)
        }
        listOfSchools?.filter { school ->
            school.themes.any { it.name.contains(filter, true) }
        }?.map { it.mapToUi() }?.toImmutableList()?.let { newList ->
            updateState { state ->
                state.copy(
                    listOfSchools = newList
                )
            }
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
            val info = listOfSchools?.first { it.id == selected.id }
            updateState {
                it.copy(
                    alertDialogInfo = info
                )
            }
        }
        return true
    }
}
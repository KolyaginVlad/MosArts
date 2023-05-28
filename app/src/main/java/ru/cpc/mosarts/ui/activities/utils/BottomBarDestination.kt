package ru.cpc.mosarts.ui.activities.utils

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import ru.cpc.mosarts.R
import ru.cpc.mosarts.ui.destinations.SchoolMapScreenDestination
import ru.cpc.mosarts.ui.destinations.SimpleTestScreenDestination
import ru.cpc.mosarts.ui.destinations.TestSelectScreenDestination

enum class BottomBarDestination(
    val direction: DirectionDestinationSpec,
    @DrawableRes val icon: Int,
    @StringRes val topBarTitle: Int,
) {
    Map(SchoolMapScreenDestination, R.drawable.baseline_map_24, R.string.school_map),
    Tests(TestSelectScreenDestination, R.drawable.baseline_notes_24, R.string.test),
//    News(NewsScreenDestination, R.drawable.notifications),
//    Course(CourseScreenDestination, R.drawable.person),
}
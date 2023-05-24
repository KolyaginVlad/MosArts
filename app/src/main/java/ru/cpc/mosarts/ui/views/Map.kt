package ru.cpc.mosarts.ui.views

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.location.FilteringMode
import com.yandex.mapkit.location.Location
import com.yandex.mapkit.location.LocationListener
import com.yandex.mapkit.location.LocationStatus
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import ru.cpc.mosarts.R
import ru.cpc.mosarts.ui.models.SchoolMapInfo


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Map(
    listOfSchool: List<SchoolMapInfo>,
    onSchoolTap: MapObjectTapListener,
    modifier: Modifier = Modifier,
) {
    val lifecycle = LocalLifecycleOwner.current
    val locationManager = remember {
        MapKitFactory.getInstance().createLocationManager()
    }
    var mapView by remember {
        mutableStateOf<MapView?>(null)
    }
    var userLocation by remember {
        mutableStateOf<Point?>(null)
    }
    val fineLocationPermissionState = rememberPermissionState(
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )
    val locationListener = remember(mapView) {
        object : LocationListener {
            override fun onLocationUpdated(location: Location) {
                if (userLocation == null) {
                    moveTo(mapView, location.position)
                }
                userLocation = location.position
            }

            override fun onLocationStatusUpdated(p0: LocationStatus) {

            }

        }
    }
    Box(modifier = modifier) {
        AndroidView(
            modifier = modifier,
            factory = { context ->
                MapView(context).apply {
                    map.move(
                        CameraPosition(
                            Point(55.740624, 37.590686), 12.0f, 0.0f, 0.0f
                        ),
                        Animation(Animation.Type.SMOOTH, 0F),
                        null
                    )
                }.also {
                    mapView = it
                    lifecycle.lifecycle.addObserver(object : DefaultLifecycleObserver {
                        override fun onStart(owner: LifecycleOwner) {
                            super.onStart(owner)
                            MapKitFactory.getInstance().onStart()
                            it.onStart()
                            locationManager.subscribeForLocationUpdates(
                                DESIRED_ACCURACY,
                                MINIMAL_TIME,
                                MINIMAL_DISTANCE,
                                USE_IN_BACKGROUND,
                                FilteringMode.OFF,
                                locationListener
                            )
                        }

                        override fun onStop(owner: LifecycleOwner) {
                            MapKitFactory.getInstance().onStop()
                            it.onStop()
                            locationManager.unsubscribe(locationListener)
                            super.onStop(owner)
                        }
                    })
                    listOfSchool.forEach { school ->
                        it.map.mapObjects.addPlacemark(
                            Point(school.latitude, school.longitude),
                            ImageProvider.fromBitmap(drawSimpleBitmap(school.id.toString()))
                        )
                    }
                    it.map.mapObjects.addTapListener(onSchoolTap)
                }
            },
            update = {
                it.map.mapObjects.clear()
                listOfSchool.forEach { school ->
                    it.map.mapObjects.addPlacemark(
                        Point(school.latitude, school.longitude),
                        ImageProvider.fromBitmap(drawSimpleBitmap(school.id.toString()))
                    )
                }
                userLocation?.let { point ->
                    it.map.mapObjects.addPlacemark(
                        point,
                        ImageProvider.fromBitmap(getBitmap(it.context, R.drawable.baseline_smartphone_24))
                    )
                }
            }
        )
        if (
            fineLocationPermissionState.status.isGranted
            || fineLocationPermissionState.status.shouldShowRationale.not()
        ) {
            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                onClick = remember(mapView, userLocation) {
                    {
                        fineLocationPermissionState.launchPermissionRequest()
                        moveTo(mapView, userLocation)
                    }
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_my_location_24),
                    contentDescription = null,
                    tint = androidx.compose.ui.graphics.Color.Unspecified
                )
            }
        }
    }
}

private fun getBitmap(context: Context, drawableRes: Int): Bitmap? {
    return ResourcesCompat.getDrawable(context.resources, drawableRes, null)?.let { drawable ->
        val canvas = Canvas()
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        canvas.setBitmap(bitmap)
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        drawable.draw(canvas)
        bitmap
    }
}

private fun drawSimpleBitmap(label: String): Bitmap {
    val picSize = 75
    val bitmap = Bitmap.createBitmap(picSize, picSize, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    // отрисовка плейсмарка
    val paint = Paint()
    paint.color = Color.BLUE
    paint.style = Paint.Style.FILL
    canvas.drawCircle(picSize / 2f, picSize / 2f, picSize / 2f, paint)
    // отрисовка текста
    paint.color = Color.WHITE
    paint.isAntiAlias = true
    paint.textSize = 30f
    paint.textAlign = Paint.Align.CENTER
    canvas.drawText(
        label, picSize / 2f,
        picSize / 2 - ((paint.descent() + paint.ascent()) / 2), paint
    )
    return bitmap
}

private fun moveTo(mapView: MapView?, location: Point?) {
    location?.let {
        mapView?.map?.move(
            CameraPosition(
                location, 12.0f, 0.0f, 0.0f
            ),
            Animation(Animation.Type.SMOOTH, 1F),
            null
        )
    }
}

private const val DESIRED_ACCURACY = 0.0
private const val MINIMAL_TIME: Long = 3000
private const val MINIMAL_DISTANCE = 2.0
private const val USE_IN_BACKGROUND = false

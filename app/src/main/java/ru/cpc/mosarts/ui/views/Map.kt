package ru.cpc.mosarts.ui.views

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObject
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import ru.cpc.mosarts.ui.models.SchoolMapInfo
import java.lang.Math.pow
import kotlin.math.pow
import kotlin.math.sqrt

@Composable
fun Map(
    listOfSchool: List<SchoolMapInfo>,
    onSchoolTap: MapObjectTapListener,
    modifier: Modifier = Modifier,
) {
    val lifecycle = LocalLifecycleOwner.current
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
                lifecycle.lifecycle.addObserver(object : DefaultLifecycleObserver {
                    override fun onStart(owner: LifecycleOwner) {
                        super.onStart(owner)
                        MapKitFactory.getInstance().onStart()
                        it.onStart()
                    }

                    override fun onStop(owner: LifecycleOwner) {
                        MapKitFactory.getInstance().onStop()
                        it.onStop()
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
        }
    )
}

fun drawSimpleBitmap(label: String): Bitmap {
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
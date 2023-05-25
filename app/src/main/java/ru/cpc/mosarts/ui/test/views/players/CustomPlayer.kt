package ru.cpc.mosarts.ui.test.views.players

import android.media.MediaPlayer
import java.io.IOException


class CustomMediaPlayer : MediaPlayer() {
	private var source: String? = null
	
	@Throws(
		IOException::class,
		IllegalArgumentException::class,
		SecurityException::class,
		IllegalStateException::class
	)
	override fun setDataSource(path: String?) {
		// TODO Auto-generated method stub
		super.setDataSource(path)
		source = path
	}
	
	fun getDataSource(): String? {
		return source
	}
}
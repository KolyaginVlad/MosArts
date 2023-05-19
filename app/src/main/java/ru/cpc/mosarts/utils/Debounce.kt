package ru.cpc.mosarts.utils

import android.os.Handler
import android.os.Looper

class Debounce(
    private val delay: Long = DELAY_DEFAULT
) {
    private val handler by lazy { Looper.myLooper()?.let(::Handler) }
    private var runnable: Runnable? = null

    operator fun invoke(block: () -> Unit) {
        runnable?.let {
            handler?.removeCallbacks(it)
        }
        handler?.postDelayed(Runnable { block() }.also { runnable = it }, delay)
    }

    fun cancel(){
        runnable?.let {
            handler?.removeCallbacks(it)
        }
        runnable = null
    }

    companion object {
        const val DELAY_DEFAULT = 300L
    }
}
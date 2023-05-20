package ru.cpc.mosarts.ui.auth

import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.api.sdk.auth.VKScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

//FIXME очень плохое решение, но на другое не хочу тратить время
object Synchronizer {
    private val _token = MutableSharedFlow<VKAuthenticationResult>(extraBufferCapacity = 1)
    val token = _token.asSharedFlow()

    private val _login = MutableSharedFlow<List<VKScope>>(extraBufferCapacity = 1)
    val login = _login.asSharedFlow()

    fun sendResult(result: VKAuthenticationResult){
        _token.tryEmit(result)
    }

    fun login(scopes: List<VKScope>) {
        _login.tryEmit(scopes)
    }
}
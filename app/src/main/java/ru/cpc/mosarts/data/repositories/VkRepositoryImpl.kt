package ru.cpc.mosarts.data.repositories

import android.content.SharedPreferences
import androidx.core.content.edit
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiJSONResponseParser
import com.vk.api.sdk.VKApiManager
import com.vk.api.sdk.VKMethodCall
import com.vk.api.sdk.internal.ApiCommand
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import ru.cpc.mosarts.domain.models.ProfileInfo
import ru.cpc.mosarts.domain.repositories.VkRepository
import javax.inject.Inject

class VkRepositoryImpl @Inject constructor(
    private val preferences: SharedPreferences,
) : VkRepository {

    override var vkToken: String?
        get() = preferences.getString(VK_TOKEN, null)
        set(value) {
            preferences.edit {
                putString(VK_TOKEN, value)
            }
        }

    override suspend fun getProfileInfo() = withContext(Dispatchers.IO) {
        VK.executeSync(GetProfileUser())
    }

    private companion object {
        const val VK_TOKEN = "vkToken"
    }
}

class GetProfileUser : ApiCommand<ProfileInfo>() {
    override fun onExecute(manager: VKApiManager): ProfileInfo {
        val call = VKMethodCall.Builder()
            .method("account.getProfileInfo")
            .version(manager.config.version)
            .build()
        return manager.execute(call, ProfileInfoResponseApiParser())
    }

    class ProfileInfoResponseApiParser : VKApiJSONResponseParser<ProfileInfo> {
        override fun parse(responseJson: JSONObject): ProfileInfo {
            val json = responseJson.getJSONObject("response")
            return ProfileInfo(
                name = json.getString("first_name"),
                surname = json.getString("last_name"),
            )
        }
    }
}
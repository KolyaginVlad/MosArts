package ru.cpc.mosarts.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProfileInfo(
    val name: String,
    val surname: String,
) : Parcelable

package com.example.portal.modules.rocket

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RocketItem(
    val missionName: String,
    val missionPatch: String,
    val launchYear: Int
) : Parcelable
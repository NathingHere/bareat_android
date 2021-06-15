package com.example.data.torecieve

import android.os.Parcelable
import com.example.data.DataProfile
import com.example.data.extension.JSONConvertable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class LoginResponse(
    @SerializedName("data") val dataProfile: DataProfile?,
    @SerializedName("token") val token: String?
    ) : Parcelable, JSONConvertable
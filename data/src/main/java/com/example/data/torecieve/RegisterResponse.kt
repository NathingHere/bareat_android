package com.example.data.torecieve

import android.os.Parcelable
import com.example.data.Data
import com.example.data.extension.JSONConvertable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RegisterResponse (
    @SerializedName("data") val data: Data?,
    @SerializedName("message") val message: String?
        ) : Parcelable, JSONConvertable
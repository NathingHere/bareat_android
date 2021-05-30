package com.example.data

import android.os.Parcelable
import com.example.data.extension.JSONConvertable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Data(
    @SerializedName("nombre") val name: String?,
    @SerializedName("apellidos") val surname: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("id") val id: Int?
) : Parcelable, JSONConvertable
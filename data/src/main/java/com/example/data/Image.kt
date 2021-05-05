package com.example.data

import android.os.Parcelable
import com.example.data.extension.JSONConvertable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Image (
        @SerializedName("id") val id: Int,
        @SerializedName("ruta_imagen") val url: String?,
        ) : Parcelable, JSONConvertable
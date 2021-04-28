package com.example.data

import android.os.Parcelable
import com.example.data.extension.JSONConvertable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Dish (
        @SerializedName("id") val id: Int,
        @SerializedName("") val name: String?,
        @SerializedName("") val type: String?,
        @SerializedName("") val desc: String?,
        @SerializedName("") val price: Double?,
        @SerializedName("") val cover: String?,
        @SerializedName("") val rating: Float?,
        ) : Parcelable, JSONConvertable
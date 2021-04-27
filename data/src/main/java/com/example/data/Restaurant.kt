package com.example.data

import android.os.Parcelable
import com.example.data.extension.JSONConvertable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Restaurant (
    @SerializedName("") val name: String?,
    @SerializedName("") val desc: String?,
    @SerializedName("") val cover: String?,
    @SerializedName("") val address: String?,
    @SerializedName("") val latitude: Double?,
    @SerializedName("") val longitude: Double?,
    @SerializedName("") val type: String?,
    @SerializedName("") val capacity: Int?,
    @SerializedName("") val rating : Float?,
    @SerializedName("") val isPremium : Boolean?
    ) : Parcelable, JSONConvertable
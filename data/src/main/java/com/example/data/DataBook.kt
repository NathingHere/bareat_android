package com.example.data

import android.os.Parcelable
import com.example.data.extension.JSONConvertable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataBook (
    @SerializedName("user_id") val userId: Int?,
    @SerializedName("establecimiento_id") val restaurantId: Int?,
    @SerializedName("num_comensales") val num: Int?,
    @SerializedName("fecha") val date: String?,
    @SerializedName("hora") val hour: String?,
    @SerializedName("id") val id: Int?,
    @SerializedName("establecimiento") val restaurant: Restaurant?
) : Parcelable, JSONConvertable
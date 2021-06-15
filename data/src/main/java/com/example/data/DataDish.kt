package com.example.data

import android.os.Parcelable
import com.example.data.extension.JSONConvertable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataDish (
        @SerializedName("user_id") val userId: Int?,
        @SerializedName("producto_id") val restaurantId: Int?,
        @SerializedName("puntuacion_producto") val rating: Int?,
        @SerializedName("comentario") val comment: String?,
        @SerializedName("id") val id: Int?
        ) : Parcelable, JSONConvertable
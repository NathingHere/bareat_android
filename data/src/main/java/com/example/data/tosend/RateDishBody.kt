package com.example.data.tosend

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RateDishBody (
    @SerializedName("puntuacion_producto") val rating: Int?,
    @SerializedName("comentario") val comment: String?
    ) : Parcelable
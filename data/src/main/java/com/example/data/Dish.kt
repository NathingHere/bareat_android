package com.example.data

import android.os.Parcelable
import com.example.data.extension.JSONConvertable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Dish (
        @SerializedName("id") val id: Int,
        @SerializedName("nombre_producto") val name: String?,
        @SerializedName("tipo_producto") val type: String?,
        @SerializedName("descripcion_producto") val desc: String?,
        @SerializedName("precio_producto") val price: Double?,
        @SerializedName("ruta_foto_principal") val cover: String?,
        @SerializedName("puntuacion_media_producto") val rating: Int?
        ) : Parcelable, JSONConvertable
package com.example.data

import android.os.Parcelable
import com.example.data.extension.JSONConvertable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Restaurant (
        @SerializedName("id") val id: Int,
    @SerializedName("nombre_establecimiento") val name: String?,
    @SerializedName("descripcion_establecimiento") val desc: String?,
    @SerializedName("ruta_foto_principal") val cover: String?,
    @SerializedName("dirección_establecimiento") val address: String?,
    @SerializedName("latitud") val latitude: Double?,
    @SerializedName("longitud") val longitude: Double?,
    @SerializedName("tipo_establecimiento") val type: String?,
    @SerializedName("maximo_numero_comensales") val capacity: Int?,
    @SerializedName("puntuacion_media_establecimiento") val rating : Float?,
    @SerializedName("es_premium") val isPremium : Boolean?,
        @SerializedName("email") val email : String?,
        @SerializedName("num_telefono") val phone : Int?
        ) : Parcelable, JSONConvertable
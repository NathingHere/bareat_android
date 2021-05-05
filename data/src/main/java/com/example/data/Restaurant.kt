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
    @SerializedName("") val cover: String?,
    @SerializedName("dirección_establecimiento") val address: String?,
    @SerializedName("latitud") val latitude: Double?,
    @SerializedName("longitud") val longitude: Double?,
    @SerializedName("tipo_establecimiento") val type: String?,
    @SerializedName("maximo_numero_comensales") val capacity: Int?,
    @SerializedName("") val rating : Float?,
    @SerializedName("es_premium") val isPremium : Boolean?,
        @SerializedName("") val email : String?,
        @SerializedName("") val phone : Int?,


        ) : Parcelable, JSONConvertable
package com.example.data

import android.os.Parcelable
import com.example.data.extension.JSONConvertable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReviewDish (
    @SerializedName("id") val id: Int,
        //@SerializedName("") val name: String?,
    @SerializedName("puntuacion_producto") var rating: Float?,
    @SerializedName("comentario") var comment: String?,
    @SerializedName("producto") val dish: Dish?
        ) : Parcelable, JSONConvertable
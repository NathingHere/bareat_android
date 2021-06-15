package com.example.data.tosend

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BookBody (
    @SerializedName("num_comensales") val num: Int?,
    @SerializedName("fecha") val date: String?,
    @SerializedName("hora") val hour: String?
    ) : Parcelable
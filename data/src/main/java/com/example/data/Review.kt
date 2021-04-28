package com.example.data

import android.os.Parcelable
import com.example.data.extension.JSONConvertable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Review (
        @SerializedName("id") val id: Int,
        @SerializedName("") val name: String?,
        @SerializedName("") val rating: Float?,
        @SerializedName("") val comment: String?,
        ) : Parcelable, JSONConvertable
package com.example.data.torecieve

import android.os.Parcelable
import com.example.data.DataDish
import com.example.data.DataProfile
import com.example.data.extension.JSONConvertable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RateDishResponse (
    @SerializedName("data") val dataDish: DataDish?,
    @SerializedName("message") val message: String?
) : Parcelable, JSONConvertable
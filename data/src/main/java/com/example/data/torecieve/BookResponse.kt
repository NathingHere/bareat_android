package com.example.data.torecieve

import android.os.Parcelable
import com.example.data.DataBook
import com.example.data.DataProfile
import com.example.data.extension.JSONConvertable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BookResponse (
    @SerializedName("data") val dataBook: DataBook?,
    @SerializedName("message") val message: String?
) : Parcelable, JSONConvertable
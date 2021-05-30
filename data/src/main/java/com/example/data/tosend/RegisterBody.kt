package com.example.data.tosend

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RegisterBody (
        @SerializedName("email") val email: String?,
        @SerializedName("password") val password: String?,
        @SerializedName("nombre") val name: String?,
        @SerializedName("apellidos") val surname: String?,
        @SerializedName("num_telefono") val tel: Int?,
        @SerializedName("fecha_nacimiento") val birthday: String?
        ) : Parcelable
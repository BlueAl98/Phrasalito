package com.nayibit.network.model

import com.google.gson.annotations.SerializedName

data class ApiErrorDto(
    @SerializedName("error") val error: String?,
    @SerializedName("message") val message: String?,
    @SerializedName("path") val path: String?,
    @SerializedName("status") val status: Int?,
    @SerializedName("timestamp") val timestamp: String?
)

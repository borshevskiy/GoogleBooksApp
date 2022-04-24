package com.borshevskiy.googlebooksapp.data.network.model


import com.google.gson.annotations.SerializedName

data class ApiResponseDto(
    @SerializedName("items")
    val items: List<Item>,
    @SerializedName("kind")
    val kind: String,
    @SerializedName("totalItems")
    val totalItems: Int
)
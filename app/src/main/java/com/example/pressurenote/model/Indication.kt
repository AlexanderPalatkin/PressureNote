package com.example.pressurenote.model

data class Indication(
    val time: String? = "",
    val upperPressure: Int? = 0,
    val lowerPressure: Int? = 0,
    val pulse: Int? = 0
)

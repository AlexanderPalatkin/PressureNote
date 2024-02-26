package com.example.pressurenote.model

import java.util.UUID

data class Indication(
    val date: String? = null,
    val time: String? = null,
    val upperPressure: String? = null,
    val lowerPressure: String? = null,
    val pulse: String? = null
) {
    val id: String = UUID.randomUUID().toString()
}

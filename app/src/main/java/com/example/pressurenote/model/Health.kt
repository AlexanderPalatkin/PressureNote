package com.example.pressurenote.model

data class Health(
    val date: String? = "",
    val indications: List<Indication>? = emptyList()
)

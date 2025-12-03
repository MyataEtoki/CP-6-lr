package com.example.a6lr

data class ParrotApiResponse(
    val parrots: List<ParrotJsonResponse>
)

data class ParrotJsonResponse(
    val id: Long,
    val name: String,
    val species: String,
    val age: Int
)
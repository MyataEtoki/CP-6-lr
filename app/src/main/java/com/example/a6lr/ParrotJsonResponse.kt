package com.example.a6lr

// когда JSON выглядит так:
// [ { "name": "Какаду", "species": "Какаду", "age": 5 }, ... ]

data class ParrotJsonResponse(
    val id: Int,
    val name: String,
    val species: String,
    val age: Int
)
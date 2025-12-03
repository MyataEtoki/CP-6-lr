package com.example.a6lr

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "parrots")
data class Parrot(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val species: String,
    val age: Int
)
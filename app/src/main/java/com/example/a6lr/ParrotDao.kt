package com.example.a6lr
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ParrotDao {
    @Query("SELECT * FROM parrots ORDER BY name ASC")
    fun getAllParrots(): Flow<List<Parrot>>

    @Insert
    suspend fun insertParrot(parrot: Parrot)

    @Update
    suspend fun updateParrot(parrot: Parrot)

    @Delete
    suspend fun deleteParrot(parrot: Parrot)

    @Query("SELECT * FROM parrots WHERE id = :id")
    suspend fun getParrotById(id: Long): Parrot?

    @Query("SELECT * FROM parrots WHERE name = :name LIMIT 1")
    suspend fun getParrotByName(name: String): Parrot?
}
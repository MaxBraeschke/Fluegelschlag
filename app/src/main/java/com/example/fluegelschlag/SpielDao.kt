package com.example.fluegelschlag

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fluegelschlag.Entities.Spiel

@Dao
interface SpielDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpiel(spiel: Spiel) : Long

    @Query("SELECT * FROM Spiel")
    fun getAlleSpiel() : LiveData<List<Spiel>>

    @Query("DELETE FROM Spiel")
    suspend fun deleteAlleSpiele()

}
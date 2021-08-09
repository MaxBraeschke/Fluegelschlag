package com.example.fluegelschlag

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import com.example.fluegelschlag.DatenKombos.SpielerGesamtPunkte
import com.example.fluegelschlag.Entities.Spieler

@Dao
interface Dao {


    @Query("SELECT * FROM Spieler")
    fun getAlleSpieler() : LiveData<List<Spieler>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSpieler(spieler: Spieler)

    @Query("DELETE FROM Spieler")
    suspend fun deleteAllSpieler()

    @Delete
    suspend fun deleteSingleSpieler(spieler: Spieler)

    @Update
    suspend fun updateSpieler(spieler: Spieler)

}
package com.example.fluegelschlag

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fluegelschlag.DatenKombos.SpielerGesamtPunkte
import com.example.fluegelschlag.Entities.SpielErgebnisse
import com.example.fluegelschlag.Entities.StatistikSpiele

@Dao
interface SpielErgebnisseDao {

    @Insert
    suspend fun insertMehrereSpielErgebnisse(vararg spielErgebnisse: SpielErgebnisse)


    @Query("SELECT * FROM SpielErgebnisse")
    fun getAlleSpielErgebnisse() : LiveData<List<SpielErgebnisse>>

    @Query("SELECT spielervorname as spielerVorname, spielernachname as spielerNachname, SUM(gesamtPunkte) as gesamtPunkte FROM SpielErgebnisse GROUP BY spielervorname, spielernachname")
    fun getSpielerUndGesamtPunkte() : LiveData<List<SpielerGesamtPunkte>>

    @Query("SELECT datum as datum, spielerVorname as gewinner, spielDauer as dauer FROM Spiel, SpielErgebnisse WHERE spiel.spielnummer = spielErgebnisse.spielnummer AND platzierung = 1")
    fun getLetzteSpiele(): LiveData<List<StatistikSpiele>>

}
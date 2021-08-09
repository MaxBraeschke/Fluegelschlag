package com.example.fluegelschlag.Entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.example.fluegelschlag.DatenKombos.SpielerGesamtPunkte

@Entity(primaryKeys = arrayOf("spielervorname", "spielnummer" , "spielernachname"),
        foreignKeys = arrayOf(
                ForeignKey(entity = Spieler::class, parentColumns = arrayOf("vorname", "nachname"), childColumns = arrayOf("spielervorname", "spielernachname"), onDelete = ForeignKey.CASCADE),
                ForeignKey(entity = Spiel::class, parentColumns = arrayOf("spielnummer"), childColumns = arrayOf("spielnummer"), onDelete = ForeignKey.CASCADE)
        ))
data class SpielErgebnisse(
        val spielervorname: String,
        val spielernachname: String,
        val spielnummer: Int,
        val vogelPunkte: Int,
        val bonuskartenPunkte: Int,
        val rundenzielePunkte: Int,
        val eierPunkte: Int,
        val gelagertesFutterPunkte: Int,
        val kartenUnterVoegelPunkte: Int,
        val gesamtPunkte: Int,
        val platzierung: Int
)

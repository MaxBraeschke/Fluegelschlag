package com.example.fluegelschlag.DatenKombos

import androidx.room.ColumnInfo

data class SpielerGesamtPunkte(
    val spielerVorname:String,
    val spielerNachname:String,
    val gesamtPunkte: Int
)

package com.example.fluegelschlag.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys = arrayOf("vorname" , "nachname"))
data class Spieler(
    val vorname: String,
    val nachname: String,
    var koennen: String
)

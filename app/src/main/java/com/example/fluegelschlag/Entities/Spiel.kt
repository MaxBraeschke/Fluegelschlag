package com.example.fluegelschlag.Entities

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.sql.Time
import java.util.*
import javax.annotation.Nullable

@Entity()
data class Spiel(
    @PrimaryKey(autoGenerate = true) var spielnummer: Int = 0,
    val spielerAnzahl: Int,
    val spielDauer: Int? = null,
    val datum: Date,
)

package com.example.fluegelschlag

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.fluegelschlag.Entities.SpielErgebnisse
import com.example.fluegelschlag.Entities.Spiel
import com.example.fluegelschlag.Entities.Spieler

//TODO Neue Entities (TAG, WOCHE) verbinden mit NutritionTabele, vllt Tage als Haupt und dann verbinden mit einer Liste, Tag als LiveData bei
// Änderung neue Tabelle

//TODO wenn sich Tag ändert, dann erst Tag mit der akt. Tabelle einspeichern und dann Tabelle akt. Reset
@Database(entities = arrayOf(SpielErgebnisse::class, Spieler::class, Spiel::class),version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class Datenbank: RoomDatabase() {

    abstract fun getDao(): Dao

    abstract fun getSpielDao(): SpielDao

    abstract fun getSpielErgebnisseDao(): SpielErgebnisseDao
}


package com.example.fluegelschlag.SpielErgebnisse

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PunkteUpdateViewModel : ViewModel() {

    lateinit var aktuelleVogelPunkte: (punkte: Int, position: Int) -> (Unit)
    lateinit var aktuelleBonuskartenPunkte: (punkte: Int, position: Int) -> (Unit)
    lateinit var aktuelleRundenZielePunkte: (punkte: Int, position: Int) -> (Unit)
    lateinit var aktuelleEierPunkte: (punkte: Int, position: Int) -> (Unit)
    lateinit var aktuelleFutterPunkte: (punkte: Int, position: Int) -> (Unit)
    lateinit var aktuelleKartenPunkte: (punkte: Int, position: Int) -> (Unit)
    lateinit var aktuelleExtraFutterPunkte: (punkte: Int, position: Int) -> (Unit)

    fun observeAktuelleVogelPunkte(listener: (Int, Int) -> (Unit)) {
        aktuelleVogelPunkte = listener
    }

    fun updateVogelPunkte(punkte: Int, position: Int) {
        viewModelScope.launch {
            println("updated")
            aktuelleVogelPunkte(punkte, position)
        }
    }

    fun updateExtraFutterPunkte(punkte: Int, position: Int){
        viewModelScope.launch {
            aktuelleExtraFutterPunkte(punkte, position)
        }
    }

    fun observeExtraFutterPunkte(listener: (Int, Int) -> Unit){
        aktuelleExtraFutterPunkte = listener
    }
    fun observeBonuskartenPunkte(listener: (Int, Int) -> (Unit)) {
        aktuelleBonuskartenPunkte = listener
    }

    fun updateBonuskartePukte(punkte: Int, position: Int) {
        viewModelScope.launch {
            println("updated")
            aktuelleBonuskartenPunkte(punkte, position)
        }
    }

    fun observeRundenZielePunkte(listener: (Int, Int) -> (Unit)) {
        aktuelleRundenZielePunkte = listener
    }

    fun updateRundenZielePunkte(punkte: Int, position: Int) {
        viewModelScope.launch {
            println("updated")
            aktuelleRundenZielePunkte(punkte, position)
        }
    }

    fun observeEierPunkte(listener: (Int, Int) -> (Unit)) {
        aktuelleEierPunkte = listener
    }

    fun updateEierPunkte(punkte: Int, position: Int) {
        viewModelScope.launch {
            println("updated")
            aktuelleEierPunkte(punkte, position)
        }
    }

    fun observeFutterPunkte(listener: (Int, Int) -> (Unit)) {
        aktuelleFutterPunkte = listener
    }

    fun updateFutterPunkte(punkte: Int, position: Int) {
        viewModelScope.launch {
            println("updated")
            aktuelleFutterPunkte(punkte, position)
        }
    }

    fun observeKartenPunkte(listener: (Int, Int) -> (Unit)) {
        aktuelleKartenPunkte = listener
    }

    fun updateKartenPunkte(punkte: Int, position: Int) {
        viewModelScope.launch {
            println("updated")
            aktuelleKartenPunkte(punkte, position)
        }
    }
}
package com.example.fluegelschlag.SpielerBag

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fluegelschlag.Dao
import com.example.fluegelschlag.Entities.Spieler
import kotlinx.coroutines.launch

class SpielerViewModel@ViewModelInject constructor(
    val dao: Dao
) : ViewModel() {

    fun getAlleSpieler() = dao.getAlleSpieler()

    fun insertSpieler(spieler: Spieler) {
        viewModelScope.launch {
            dao.insertSpieler(spieler)
        }
    }

    fun updateSpieler(spieler: Spieler) {
        viewModelScope.launch {
            dao.updateSpieler(spieler)
        }
    }

    fun deleteAllSpieler() {
        viewModelScope.launch {
            dao.deleteAllSpieler()
        }
    }

    fun deleteSingleSpieler(spieler: Spieler) {
        viewModelScope.launch {
            dao.deleteSingleSpieler(spieler)
        }
    }
}
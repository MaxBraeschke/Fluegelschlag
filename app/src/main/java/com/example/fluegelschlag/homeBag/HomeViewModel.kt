package com.example.fluegelschlag.homeBag

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.fluegelschlag.SpielDao
import com.example.fluegelschlag.SpielErgebnisseDao


class HomeViewModel@ViewModelInject constructor(
    val dao: SpielDao,
    val spielErgebnisseDao: SpielErgebnisseDao
) : ViewModel() {

    fun getAlleSpiele() = dao.getAlleSpiel()

    fun getBestenListe() = spielErgebnisseDao.getSpielerUndGesamtPunkte()

    fun getLetzteSpiele() = spielErgebnisseDao.getLetzteSpiele()

}

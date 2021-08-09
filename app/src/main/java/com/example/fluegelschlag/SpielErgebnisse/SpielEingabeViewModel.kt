package com.example.fluegelschlag.SpielErgebnisse

import android.graphics.PostProcessor
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fluegelschlag.DatenKombos.VorlaufigSpielErgebnisse
import com.example.fluegelschlag.Entities.Spiel
import com.example.fluegelschlag.Entities.SpielErgebnisse
import com.example.fluegelschlag.Entities.Spieler
import com.example.fluegelschlag.SpielDao
import com.example.fluegelschlag.SpielErgebnisseDao
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.sql.Date
import java.sql.Time
import java.time.LocalDate
import java.time.LocalTime
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.ArrayList
import kotlin.time.ExperimentalTime
import kotlin.time.hours

@Singleton
class SpielEingabeViewModel @Inject constructor(
    val spielErgebnisseDao: SpielErgebnisseDao,
    val spieldao: SpielDao
) : ViewModel() {

    val aktuellerSpielerList: ArrayList<Spieler> = arrayListOf()

    val aktuellerSpielErgebnisseList = arrayListOf<VorlaufigSpielErgebnisse>(
    )
    var saveTime = false
    var startTime: Int = 0
    var endTime: Int = 0

    fun clearLists() {
        aktuellerSpielerList.clear()
        aktuellerSpielErgebnisseList.clear()
    }

    fun updateSpielerList(newList: ArrayList<Spieler>) {
        aktuellerSpielerList.addAll(newList)

        for (i in newList.indices) {
            aktuellerSpielErgebnisseList.add(
                VorlaufigSpielErgebnisse(
                    i,
                    spielervorname = newList[i].vorname, spielernachname = newList[i].nachname, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0
                )
            )
        }

    }

    fun getAlleSpielErgebnisse() = spielErgebnisseDao.getAlleSpielErgebnisse()

    @ExperimentalTime
    fun spielUndErgebnisseSpeichern() {
        viewModelScope.launch {
            println(startTime)

            endTime = LocalTime.now().minute
            println(endTime)
            val time = (endTime - startTime)
            val listOfNewErgebnisse: ArrayList<SpielErgebnisse> = arrayListOf<SpielErgebnisse>()

            val latestSpiel: Deferred<Long>
            if(saveTime) {
                latestSpiel =
                    async { spieldao.insertSpiel(Spiel(spielerAnzahl = aktuellerSpielerList.size,spielDauer = time, datum = java.util.Date())) }
            } else {
                latestSpiel =
                    async { spieldao.insertSpiel(Spiel(spielerAnzahl = aktuellerSpielerList.size, datum = java.util.Date())) }
            }

            println(latestSpiel)
            for (i in aktuellerSpielErgebnisseList.indices) {
                val gesamtPunkte =
                    aktuellerSpielErgebnisseList[i].vogelPunkte + aktuellerSpielErgebnisseList[i].bonuskartenPunkte + aktuellerSpielErgebnisseList[i].rundenzielePunkte +
                            aktuellerSpielErgebnisseList[i].eierPunkte + aktuellerSpielErgebnisseList[i].gelagertesFutterPunkte + aktuellerSpielErgebnisseList[i].kartenUnterVoegelPunkte

                listOfNewErgebnisse.add(
                    SpielErgebnisse(
                        spielervorname = aktuellerSpielErgebnisseList[i].spielervorname,
                        spielernachname = aktuellerSpielErgebnisseList[i].spielernachname,
                        latestSpiel.await().toInt(),
                        aktuellerSpielErgebnisseList[i].vogelPunkte,
                        aktuellerSpielErgebnisseList[i].bonuskartenPunkte,
                        aktuellerSpielErgebnisseList[i].rundenzielePunkte,
                        aktuellerSpielErgebnisseList[i].eierPunkte,
                        aktuellerSpielErgebnisseList[i].gelagertesFutterPunkte,
                        aktuellerSpielErgebnisseList[i].kartenUnterVoegelPunkte,
                        gesamtPunkte,
                        aktuellerSpielErgebnisseList[i].platzierung
                    )
                )

            }

            for (i in listOfNewErgebnisse.indices) {
                println(listOfNewErgebnisse[i])
            }

            spielErgebnisseDao.insertMehrereSpielErgebnisse(
                *listOfNewErgebnisse.toTypedArray()
            )
        }
    }

    fun getRanked(): List<VorlaufigSpielErgebnisse> {
        return aktuellerSpielErgebnisseList.sortedBy { it.platzierung }
    }
    fun updateExtraFutterPunkte(punkte: Int, position: Int){
        aktuellerSpielErgebnisseList[position].extraFutter = punkte
    }

    fun updateVogelPunkte(punkte: Int, position: Int) {
        aktuellerSpielErgebnisseList[position].vogelPunkte = punkte
    }

    fun updateBonuskartenPunkte(punkte: Int, position: Int) {
        aktuellerSpielErgebnisseList[position].bonuskartenPunkte = punkte
    }

    fun updateRundenzielePunkte(punkte: Int, position: Int) {
        aktuellerSpielErgebnisseList[position].rundenzielePunkte = punkte
    }

    fun updateEierPunkte(punkte: Int, position: Int) {
        aktuellerSpielErgebnisseList[position].eierPunkte = punkte
    }

    fun updateFutterPunkte(punkte: Int, position: Int) {
        aktuellerSpielErgebnisseList[position].gelagertesFutterPunkte = punkte
    }

    fun updateKartenPunkte(punkte: Int, position: Int) {
        aktuellerSpielErgebnisseList[position].kartenUnterVoegelPunkte = punkte
    }

    fun updateGesamtPunkte() {
        for (i in aktuellerSpielErgebnisseList.indices) {
            val gesamtPunkte =
                aktuellerSpielErgebnisseList[i].vogelPunkte + aktuellerSpielErgebnisseList[i].bonuskartenPunkte + aktuellerSpielErgebnisseList[i].rundenzielePunkte +
                        aktuellerSpielErgebnisseList[i].eierPunkte + aktuellerSpielErgebnisseList[i].gelagertesFutterPunkte + aktuellerSpielErgebnisseList[i].kartenUnterVoegelPunkte
            aktuellerSpielErgebnisseList[i].gesamtPunkte = gesamtPunkte
        }
    }

    fun updateRank() {
        for (spieler1 in aktuellerSpielErgebnisseList.indices) {
            var rank = aktuellerSpielErgebnisseList.size
            for (spieler2 in aktuellerSpielErgebnisseList.indices) {
                if (aktuellerSpielErgebnisseList[spieler1].id != aktuellerSpielErgebnisseList[spieler2].id) {
                    when {
                        aktuellerSpielErgebnisseList[spieler1].gesamtPunkte.compareTo(
                            aktuellerSpielErgebnisseList[spieler2].gesamtPunkte
                        ) > 0 -> rank--
                        aktuellerSpielErgebnisseList[spieler1].gesamtPunkte.compareTo(
                            aktuellerSpielErgebnisseList[spieler2].gesamtPunkte
                        ) == 0 -> {
                            when {
                                aktuellerSpielErgebnisseList[spieler1].extraFutter.compareTo(
                                    aktuellerSpielErgebnisseList[spieler2].extraFutter
                                ) > 0 -> rank--
                                aktuellerSpielErgebnisseList[spieler1].extraFutter.compareTo(
                                    aktuellerSpielErgebnisseList[spieler2].extraFutter
                                ) == 0 -> rank--
                            }
                        }
                    }
                }
            }
            aktuellerSpielErgebnisseList[spieler1].platzierung = rank
        }
    }

    fun gleicheGesamtPunkte(): ArrayList<VorlaufigSpielErgebnisse> {
        val habenGleichePunkte = arrayListOf<VorlaufigSpielErgebnisse>()
        for (spieler1 in aktuellerSpielErgebnisseList.indices) {
            for (spieler2 in aktuellerSpielErgebnisseList.indices) {
                if (aktuellerSpielErgebnisseList[spieler1].id != aktuellerSpielErgebnisseList[spieler2].id) {
                    if (aktuellerSpielErgebnisseList[spieler1].gesamtPunkte == aktuellerSpielErgebnisseList[spieler2].gesamtPunkte) {
                        habenGleichePunkte.add(aktuellerSpielErgebnisseList[spieler1])
                        habenGleichePunkte.add(aktuellerSpielErgebnisseList[spieler2])
                    }
                }
            }
        }
        return habenGleichePunkte
    }
}
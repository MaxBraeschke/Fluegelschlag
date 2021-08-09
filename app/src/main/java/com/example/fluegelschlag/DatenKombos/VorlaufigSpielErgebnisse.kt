package com.example.fluegelschlag.DatenKombos

import java.util.*

data class VorlaufigSpielErgebnisse(
    val id: Int,
    val spielervorname: String,
    val spielernachname: String,
    var spielnummer: Int,
    var vogelPunkte: Int,
    var bonuskartenPunkte: Int,
    var rundenzielePunkte: Int,
    var eierPunkte: Int,
    var gelagertesFutterPunkte: Int,
    var kartenUnterVoegelPunkte: Int,
    var gesamtPunkte: Int,
    var platzierung: Int,
    var extraFutter: Int
)

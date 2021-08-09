package com.example.fluegelschlag.recyclerAdpater.PunkteArtenAdapter

import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.example.fluegelschlag.Entities.Spieler

interface adapterInterface {

    fun getTheDiffer(): AsyncListDiffer<Spieler>

}
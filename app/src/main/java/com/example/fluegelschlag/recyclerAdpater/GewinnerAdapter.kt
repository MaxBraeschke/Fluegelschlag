package com.example.fluegelschlag.recyclerAdpater

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fluegelschlag.DatenKombos.SpielerGesamtPunkte
import com.example.fluegelschlag.DatenKombos.VorlaufigSpielErgebnisse
import com.example.fluegelschlag.R
import org.w3c.dom.Text

class GewinnerAdapter() : RecyclerView.Adapter<GewinnerAdapter.ExampleViewHolder>() {


    private val differCallback = object : DiffUtil.ItemCallback<VorlaufigSpielErgebnisse>() {
        override fun areItemsTheSame(oldItem: VorlaufigSpielErgebnisse, newItem: VorlaufigSpielErgebnisse): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: VorlaufigSpielErgebnisse, newItem: VorlaufigSpielErgebnisse): Boolean {
            return oldItem.gesamtPunkte == newItem.gesamtPunkte
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.spiel_ergebnis_liste, parent, false)
        return ExampleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = differ.currentList[position]

        holder.rank.text = currentItem.platzierung.toString() + "."
        holder.vorname.text = currentItem.spielervorname
        holder.nachname.text = currentItem.spielernachname
        holder.punkte.text = currentItem.gesamtPunkte.toString()
    }


    override fun getItemCount() = differ.currentList.size

    class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rank: TextView = itemView.findViewById(R.id.spiel_ergebnis_position)
        val vorname: TextView = itemView.findViewById(R.id.spiel_ergebnis_vorname)
        val nachname: TextView = itemView.findViewById(R.id.spiel_ergebnis_nachname)
        val punkte: TextView = itemView.findViewById(R.id.spiel_ergebnis_punkte)
    }
}
package com.example.fluegelschlag.recyclerAdpater

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fluegelschlag.DatenKombos.SpielerGesamtPunkte
import com.example.fluegelschlag.Entities.Spieler
import com.example.fluegelschlag.R
import dagger.hilt.android.AndroidEntryPoint


class BestenlisteAdapter() : RecyclerView.Adapter<BestenlisteAdapter.ExampleViewHolder>() {


    private val differCallback = object : DiffUtil.ItemCallback<SpielerGesamtPunkte>() {
        override fun areItemsTheSame(oldItem: SpielerGesamtPunkte, newItem: SpielerGesamtPunkte): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: SpielerGesamtPunkte, newItem: SpielerGesamtPunkte): Boolean {
            return oldItem.spielerVorname == newItem.spielerVorname && oldItem.spielerNachname == newItem.spielerNachname && oldItem.gesamtPunkte == newItem.gesamtPunkte
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.home_bestenliste, parent, false)
        return ExampleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = differ.currentList[position]

        holder.spielername.text = currentItem.spielerVorname
        holder.punkte.text = currentItem.gesamtPunkte.toString()
        holder.platzierung.text = (position + 1).toString() + "."
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        Log.i("rcy", "attached")
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        Log.i("rcy", "dettached")
    }

    override fun getItemCount() = differ.currentList.size

    class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val spielername: TextView = itemView.findViewById(R.id.home_bestenliste_Name)
        val punkte: TextView = itemView.findViewById(R.id.home_bestenliste_Punkte)
        val platzierung: TextView = itemView.findViewById(R.id.home_bestenliste_platzierung)
    }
}
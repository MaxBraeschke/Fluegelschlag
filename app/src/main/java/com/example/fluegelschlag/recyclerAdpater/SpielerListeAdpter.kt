package com.example.fluegelschlag.recyclerAdpater

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.compose.ui.res.colorResource
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fluegelschlag.Entities.Spieler
import com.example.fluegelschlag.R
import com.example.fluegelschlag.SpielerBag.SpielerFragment
import com.google.android.material.button.MaterialButtonToggleGroup

class SpielerListeAdpter() : RecyclerView.Adapter<SpielerListeAdpter.ExampleViewHolder>() {

    lateinit var observer: SpielerFragment

    fun addListener(spielerFragment: SpielerFragment) {
        observer = spielerFragment
    }


    private val differCallback = object : DiffUtil.ItemCallback<Spieler>() {
        override fun areItemsTheSame(oldItem: Spieler, newItem: Spieler): Boolean {
            return oldItem.vorname == newItem.vorname && oldItem.nachname == newItem.nachname
        }

        override fun areContentsTheSame(oldItem: Spieler, newItem: Spieler): Boolean {
            return oldItem.koennen == newItem.koennen
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.spieler_spielerliste, parent, false)
        println("SpielerAdapterErstellt")
        return ExampleViewHolder(itemView)

    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = differ.currentList[position]

        holder.spielername.text = currentItem.vorname + " " + currentItem.nachname
        holder.fortschritt.text = currentItem.koennen
        holder.card.isClickable = true
        holder.card.isLongClickable = true

        holder.card.setOnLongClickListener {
            observer.onLongClick(currentItem)
            true
        }

    }
    override fun getItemCount() = differ.currentList.size

    class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val spielername: TextView = itemView.findViewById(R.id.spieler_liste_name)
        val fortschritt: TextView = itemView.findViewById(R.id.spieler_liste_fortschritt)

        val card: CardView = itemView.findViewById(R.id.spieler_liste_card)
        val view: View = itemView.rootView

    }



}
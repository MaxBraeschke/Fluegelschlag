package com.example.fluegelschlag.recyclerAdpater

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fluegelschlag.DatenKombos.SpielerGesamtPunkte
import com.example.fluegelschlag.Entities.Spieler
import com.example.fluegelschlag.R
import com.example.fluegelschlag.homeBag.HomeAddgameSheet
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.card.MaterialCardView

class AddGameAddSpielerAdapter :
    RecyclerView.Adapter<AddGameAddSpielerAdapter.ExampleViewHolder>() {

    lateinit var observer: HomeAddgameSheet

    fun addObserver(homeAddgameSheet: HomeAddgameSheet) {
        observer = homeAddgameSheet
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
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.spieler_spielerliste, parent, false)
        return ExampleViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: AddGameAddSpielerAdapter.ExampleViewHolder,
        position: Int
    ) {
        val currentItem = differ.currentList[position]

        holder.spielername.text = currentItem.vorname + " " + currentItem.nachname
        holder.fortschritt.text = currentItem.koennen

        holder.card.isClickable = true
        holder.card.isLongClickable = false
        holder.card.setOnClickListener {
            if (!holder.clicked) {
                holder.clicked = true
                holder.card.strokeColor = holder.rootView.context.getColor(R.color.primaryDarkColor)
                holder.card.setCardBackgroundColor(holder.rootView.context.getColor(R.color.primaryLightColor))
                observer.spielerAusgewaehlt(currentItem)
            } else {
                holder.clicked = false
                holder.card.strokeColor = holder.rootView.context.getColor(R.color.white_50)
                holder.card.setCardBackgroundColor(holder.rootView.context.getColor(R.color.white_50))
                observer.spielerNichtMehrAusgewaehlt(currentItem)
            }

        }

    }

    override fun getItemCount() = differ.currentList.size

    class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val spielername: TextView = itemView.findViewById(R.id.spieler_liste_name)
        val fortschritt: TextView = itemView.findViewById(R.id.spieler_liste_fortschritt)
        val card: MaterialCardView = itemView.findViewById(R.id.spieler_liste_card)
        var clicked = false
        val rootView = itemView.rootView
    }


}
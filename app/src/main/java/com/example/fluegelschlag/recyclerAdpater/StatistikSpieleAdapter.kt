package com.example.fluegelschlag.recyclerAdpater

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fluegelschlag.Entities.Spieler
import com.example.fluegelschlag.Entities.StatistikSpiele
import com.example.fluegelschlag.R
import com.example.fluegelschlag.SpielerBag.SpielerFragment
import java.text.SimpleDateFormat

class StatistikSpieleAdapter : RecyclerView.Adapter<StatistikSpieleAdapter.ExampleViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<StatistikSpiele>() {
        override fun areItemsTheSame(oldItem: StatistikSpiele, newItem: StatistikSpiele): Boolean {
            return false
        }

        override fun areContentsTheSame(oldItem: StatistikSpiele, newItem: StatistikSpiele): Boolean {
            return false
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.statistik_fragment_spiele_rv, parent, false)
        return ExampleViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = differ.currentList[position]

        holder.datum.text = SimpleDateFormat("MM.dd.yyyy").format(currentItem.datum);
        holder.dauer.text = currentItem.dauer.toString()
        holder.gewinner.text = currentItem.gewinner

    }
    override fun getItemCount() = differ.currentList.size

    class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val datum : TextView = itemView.findViewById(R.id.statistik_spiele_datum)
        val gewinner : TextView = itemView.findViewById(R.id.statistik_spiele_gewinner)
        val dauer : TextView = itemView.findViewById(R.id.statistik_spiele_dauer)

    }



}
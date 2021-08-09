package com.example.fluegelschlag.recyclerAdpater

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fluegelschlag.Entities.Spieler
import com.example.fluegelschlag.R
import com.example.fluegelschlag.SpielErgebnisse.SpielEingabeViewModel
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class SpielEingabeAdapter @Inject constructor(
    spielViewModel: SpielEingabeViewModel
): RecyclerView.Adapter<SpielEingabeAdapter.ExampleViewHolder>() {

    val spielViewModel = spielViewModel

    private val differCallback = object : DiffUtil.ItemCallback<Spieler>() {
        override fun areItemsTheSame(oldItem: Spieler, newItem: Spieler): Boolean {
            return true
        }

        override fun areContentsTheSame(oldItem: Spieler, newItem: Spieler): Boolean {
            return true
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.spiel_eingabe_liste, parent, false)

        return ExampleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = differ.currentList[position]
        println(this)

            holder.name.text = "${currentItem.vorname}"
            holder.nachname.text = "${currentItem.nachname}"


        holder.editText.addTextChangedListener {
            holder.name.text = "LUL"
        }

    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        Log.i("rcy", "attached")
    }

    override fun onViewRecycled(holder: ExampleViewHolder) {
        super.onViewRecycled(holder)
        Log.i("rcy", "recycled")
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        Log.i("rcy", "dettached")
    }

    override fun getItemCount() = differ.currentList.size

    class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nachname: TextView = itemView.findViewById(R.id.spiel_eingabe_nachname)
        val name: TextView = itemView.findViewById(R.id.spiel_eingabe_name)
        val editText: EditText = itemView.findViewById(R.id.spiel_eingabe_zahl)
        val rootView = itemView.rootView
    }
}
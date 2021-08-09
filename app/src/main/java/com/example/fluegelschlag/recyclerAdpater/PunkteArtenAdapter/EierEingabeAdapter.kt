package com.example.fluegelschlag.recyclerAdpater.PunkteArtenAdapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fluegelschlag.Entities.Spieler
import com.example.fluegelschlag.R
import com.example.fluegelschlag.SpielErgebnisse.PunkteUpdateViewModel
import com.example.fluegelschlag.SpielErgebnisse.SpielEingabeViewModel
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.scopes.ActivityScoped
import org.w3c.dom.Text
import javax.inject.Inject


class EierEingabeAdapter constructor(
    val viewModel: PunkteUpdateViewModel
) : RecyclerView.Adapter<EierEingabeAdapter.ExampleViewHolder>(), adapterInterface {

    val listEditText: ArrayList<Pair<TextInputEditText, Int>> = arrayListOf()
    private val differCallback = object : DiffUtil.ItemCallback<Spieler>() {
        override fun areItemsTheSame(oldItem: Spieler, newItem: Spieler): Boolean {
            return true
        }

        override fun areContentsTheSame(oldItem: Spieler, newItem: Spieler): Boolean {
            return true
        }
    }

    fun getEditTextViews(): ArrayList<Pair<TextInputEditText, Int>> {
        return listEditText
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun getTheDiffer(): AsyncListDiffer<Spieler> {
        return differ
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.spiel_eingabe_liste, parent, false)

        return ExampleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = differ.currentList[position]
        listEditText.add(Pair(holder.editText, position))

        holder.name.text = "${currentItem.vorname}"
        holder.nachname.text = "${currentItem.nachname}"
        holder.editText.doAfterTextChanged {
            if (it.toString().isNotEmpty()) {viewModel.updateEierPunkte(it.toString().toInt(), position)}
            else {
                viewModel.updateEierPunkte(0,position)
            }

        }

    }

    override fun getItemCount() = differ.currentList.size

    class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nachname: TextView = itemView.findViewById(R.id.spiel_eingabe_nachname)
        val name: TextView = itemView.findViewById(R.id.spiel_eingabe_name)
        val editText: TextInputEditText = itemView.findViewById(R.id.spiel_eingabe_zahl)
        val rootView = itemView.rootView
    }
}
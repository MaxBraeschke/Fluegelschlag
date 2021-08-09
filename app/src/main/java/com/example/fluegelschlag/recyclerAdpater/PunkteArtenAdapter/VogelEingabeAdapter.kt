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
import kotlinx.android.synthetic.main.spiel_eingabe_liste.view.*
import javax.inject.Inject

class VogelEingabeAdapter constructor(
    val viewModel: PunkteUpdateViewModel
) : RecyclerView.Adapter<VogelEingabeAdapter.ExampleViewHolder>(), adapterInterface {

    val listEditText: ArrayList<Pair<TextInputEditText, Int>> = arrayListOf()

    private val differCallback = object : DiffUtil.ItemCallback<Spieler>() {
        override fun areItemsTheSame(oldItem: Spieler, newItem: Spieler): Boolean {
            return true
        }

        override fun areContentsTheSame(oldItem: Spieler, newItem: Spieler): Boolean {
            return true
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun getTheDiffer(): AsyncListDiffer<Spieler> {
        return differ
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.spiel_eingabe_liste, parent, false)
        println("Bub")
        return ExampleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = differ.currentList[position]

        holder.name.text = "${currentItem.vorname}"
        holder.nachname.text = "${currentItem.nachname}"
        listEditText.add(Pair(holder.editText, position))
        holder.editText.doAfterTextChanged {
            if (it.toString().isNotEmpty()) {viewModel.updateVogelPunkte(it.toString().toInt(), position)}
            else {
                viewModel.updateVogelPunkte(0,position)
            }

        }

        /*if (spielViewModel.getVogelPunkte(position) != -1) {
            holder.editText.setText(spielViewModel.getVogelPunkte(position).toString())
        }*/
    }

    fun getEditTextViews(): ArrayList<Pair<TextInputEditText, Int>> {
        return listEditText
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
        val editText: TextInputEditText = itemView.findViewById(R.id.spiel_eingabe_zahl)

    }


}
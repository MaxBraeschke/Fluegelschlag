package com.example.fluegelschlag.SpielErgebnisse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fluegelschlag.R
import com.example.fluegelschlag.recyclerAdpater.GewinnerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.spiel_fragment_ergebnis.view.*
import javax.inject.Inject
import kotlin.time.ExperimentalTime

@AndroidEntryPoint
class SpielErgebnisFragment : Fragment(R.layout.spiel_fragment_ergebnis) {

    @Inject
    lateinit var spielEingabeViewModel: SpielEingabeViewModel

    val gewinnerAdapter = GewinnerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.spiel_fragment_ergebnis, container, false)

        v.spiel_ergebnis_recyclerView.apply {
            adapter = gewinnerAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
        return v
    }

    @ExperimentalTime
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("ViewCreated")
        println("${spielEingabeViewModel.getRanked()[0]}")
        gewinnerAdapter.differ.submitList(spielEingabeViewModel.getRanked())

        view.spiel_activity_speichern.setOnClickListener {
            spielEingabeViewModel.spielUndErgebnisseSpeichern()
            requireActivity().finish()
        }
    }
}
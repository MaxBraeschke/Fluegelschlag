package com.example.fluegelschlag.homeBag


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fluegelschlag.DatenKombos.SpielerGesamtPunkte
import com.example.fluegelschlag.R
import com.example.fluegelschlag.SpielerBag.SpielerAddSheet
import com.example.fluegelschlag.recyclerAdpater.BestenlisteAdapter
import com.example.fluegelschlag.recyclerAdpater.StatistikSpieleAdapter
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.home_fragment.view.*
import kotlinx.android.synthetic.main.spieler_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.home_fragment) {
    private val viewModel:HomeViewModel by viewModels()

    @Inject
    lateinit var bestenlisteAdapter: BestenlisteAdapter

    @Inject
    lateinit var statistikSpieleAdapter: StatistikSpieleAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.home_fragment, container, false)

        val bestenListe = v.home_bestenliste
        bestenListe.apply {
            adapter = bestenlisteAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(false)
        }

        val letzteSpiele = v.home_letzteSpiele
        letzteSpiele.apply {
            adapter = statistikSpieleAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(false)
        }

        v.home_addGame.setOnClickListener {
            val sheet = HomeAddgameSheet()
            sheet.show(activity?.supportFragmentManager!!, "homeAddgameSheet")
        }

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getBestenListe().observe(viewLifecycleOwner, Observer {
            val sortierteListe = it.sortedByDescending { (it.gesamtPunkte) }
            val gekuerzteListe: ArrayList<SpielerGesamtPunkte> = arrayListOf()
            if (sortierteListe.size > 5) {
                for (i in 0..4) {
                    gekuerzteListe.add(sortierteListe[i])
                }
                bestenlisteAdapter.differ.submitList(gekuerzteListe)
            } else {
                bestenlisteAdapter.differ.submitList(sortierteListe)
            }
        })

        viewModel.getLetzteSpiele().observe(viewLifecycleOwner, {
            statistikSpieleAdapter.differ.submitList(it)
        })
    }
}
package com.example.fluegelschlag.SpielerBag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fluegelschlag.Entities.Spieler
import com.example.fluegelschlag.R
import com.example.fluegelschlag.recyclerAdpater.SpielerListeAdpter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.spieler_fragment.view.*
import javax.inject.Inject
@AndroidEntryPoint
class SpielerFragment : Fragment(R.layout.spieler_fragment){
    @Inject
    lateinit var spielerListeAdpter: SpielerListeAdpter
    private val viewModel: SpielerViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.spieler_fragment, container, false)

        val liste = v.spieler_liste

        spielerListeAdpter.addListener(this)

        liste.apply {
            adapter = spielerListeAdpter
            layoutManager = GridLayoutManager(context,2)
            setHasFixedSize(false)
        }

        v.spieler_fab.setOnClickListener {
            val sheet = SpielerAddSheet()
            sheet.show(activity?.supportFragmentManager!!, "spielerAddSheet")

        }

        v.spieler_add_tester.setOnClickListener {
                viewModel.insertSpieler(Spieler("Max", "Toll", "Profi"))
                viewModel.insertSpieler(Spieler("Max1", "Toll", "Profi"))
                viewModel.insertSpieler(Spieler("Max2", "Toll", "Profi"))
                viewModel.insertSpieler(Spieler("Max3", "Toll", "Profi"))
                viewModel.insertSpieler(Spieler("Max4", "Toll", "Profi"))
                viewModel.insertSpieler(Spieler("Max5", "Toll", "Profi"))
                viewModel.insertSpieler(Spieler("Max6", "Toll", "Profi"))
                viewModel.insertSpieler(Spieler("Max7", "Toll", "Profi"))
                viewModel.insertSpieler(Spieler("Max8", "Toll", "Profi"))
                viewModel.insertSpieler(Spieler("Max9", "Toll", "Profi"))
                viewModel.insertSpieler(Spieler("Max12", "Toll", "Profi"))
                viewModel.insertSpieler(Spieler("Max123", "Toll", "Profi"))
        }

        return v
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeSpielerListe()
        println("Spieler Fragment Erstellt")
    }

    private fun observeSpielerListe() {
        viewModel.getAlleSpieler().observe(viewLifecycleOwner, Observer {
            spielerListeAdpter.differ.submitList(it)
        })
    }

    fun onLongClick(spieler:Spieler) {
        val sheet = SpielerEditSheet(spieler)
        sheet.show(activity?.supportFragmentManager!!, "spielerEditSheet")
    }
}
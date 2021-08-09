package com.example.fluegelschlag.homeBag

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.fluegelschlag.Activities.SpielBlattActivity
import com.example.fluegelschlag.Entities.Spieler
import com.example.fluegelschlag.R
import com.example.fluegelschlag.SpielErgebnisse.SpielEingabeViewModel
import com.example.fluegelschlag.SpielerBag.SpielerViewModel
import com.example.fluegelschlag.recyclerAdpater.AddGameAddSpielerAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.home_addgame_sheet.view.*
import java.sql.Date
import java.sql.Time
import java.time.LocalTime
import javax.inject.Inject
import kotlin.time.ExperimentalTime
import kotlin.time.hours
import kotlin.time.minutes

@AndroidEntryPoint
class HomeAddgameSheet : BottomSheetDialogFragment(){
    private val spielerViewModel: SpielerViewModel by viewModels()
    private val spielViewModel: HomeViewModel by viewModels()
    @Inject
    lateinit var spielEingabeViewModel: SpielEingabeViewModel
    @Inject
    lateinit var addGameAddSpielerAdapter: AddGameAddSpielerAdapter
    var aktuelleAnzahl = 0
    val list: ArrayList<Spieler> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.home_addgame_sheet, container, false)

        val recyclerView = v.home_addGame_recyclerView

        addGameAddSpielerAdapter.addObserver(this)

        recyclerView.apply {
            adapter = addGameAddSpielerAdapter
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(false)
        }

        return v
    }
    @ExperimentalTime
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (dialog as BottomSheetDialog).behavior.isHideable = true
        observeList()

        view.home_addGame_add.setOnClickListener {
            spielEingabeViewModel.updateSpielerList(list)

            if (view.home_addGame_zeit.isChecked) {
                spielEingabeViewModel.saveTime = true
                spielEingabeViewModel.startTime = LocalTime.now().minute
            }

            val intent = Intent(activity, SpielBlattActivity::class.java)
            startActivity(intent)
        }
    }

    fun spielerAusgewaehlt(spieler: Spieler) {
        list.add(spieler)
        aktuelleAnzahl++
        this.requireView().home_addGame_aktuelleAnzahl_zahl.text = aktuelleAnzahl.toString()
        testMax()
    }

    fun spielerNichtMehrAusgewaehlt(spieler: Spieler) {
        list.remove(spieler)
        aktuelleAnzahl--
        this.requireView().home_addGame_aktuelleAnzahl_zahl.text = aktuelleAnzahl.toString()
        testMax()
    }

    private fun testMax() {
        val v = this.requireView()
        if(aktuelleAnzahl in 1..5) {
            v.home_addGame_aktuelleAnzahl_text.setTextColor(v.context.getColor(R.color.primaryDarkColor))
            v.home_addGame_aktuelleAnzahl_zahl.setTextColor(v.context.getColor(R.color.primaryDarkColor))
            v.home_addGame_add.isEnabled = true
        }else {
            v.home_addGame_aktuelleAnzahl_zahl.setTextColor(v.context.getColor(R.color.red_600))
            v.home_addGame_aktuelleAnzahl_text.setTextColor(v.context.getColor(R.color.red_600))

            v.home_addGame_add.isEnabled = false
        }
    }

    private fun observeList() {
        spielerViewModel.getAlleSpieler().observe(viewLifecycleOwner, Observer {
            addGameAddSpielerAdapter.differ.submitList(it)

        })
    }
}
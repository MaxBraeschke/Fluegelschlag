package com.example.fluegelschlag.SpielErgebnisse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fluegelschlag.R
import com.example.fluegelschlag.recyclerAdpater.PunkteArtenAdapter.ExtraFutterAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.spiel_fragment_zwischenfenster.view.*
import javax.inject.Inject

@AndroidEntryPoint
class SpielZwischenFragment : Fragment(R.layout.spiel_fragment_zwischenfenster) {

    @Inject
    lateinit var spielEingabeViewModel: SpielEingabeViewModel

    val punkteUpdateViewModel: PunkteUpdateViewModel = PunkteUpdateViewModel()

    private val extraFutterAdapter = ExtraFutterAdapter(punkteUpdateViewModel)

    private val extraFutterPunkteFelder = arrayListOf<TextView>()


    private var viewSafe: View? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (viewSafe == null) {
            val v = inflater.inflate(R.layout.spiel_fragment_zwischenfenster, container, false)
            v.spiel_gelagertesFutter_list.apply {
                visibility = View.VISIBLE
                adapter = extraFutterAdapter
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(false)
            }
            extraFutterAdapter.differ.submitList(spielEingabeViewModel.aktuellerSpielerList)
            createTables(v)
            createListener()
            v.spiel_activity_weiter.setOnClickListener {
                for (i in extraFutterPunkteFelder.indices) {
                    spielEingabeViewModel.updateExtraFutterPunkte(
                        extraFutterPunkteFelder[i].text.toString().toInt(), i
                    )
                }
                spielEingabeViewModel.updateRank()
                requireActivity().currentFocus?.clearFocus()
                findNavController().navigate(R.id.action_spielZwischenFragment_to_spielErgbenisFragment)

            }
            viewSafe = v
            return v
        } else {
            return viewSafe
        }
    }

    private fun createListener() {
        punkteUpdateViewModel.observeExtraFutterPunkte { punkte, position ->
            extraFutterPunkteFelder[position].text = punkte.toString()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun createTables(view: View) {
        val tablelist = view.spiel_extraFutter_table
        val spielerList = spielEingabeViewModel.aktuellerSpielerList
        val tableRowName = TableRow(context)
        val tableRowPunkte = TableRow(context)
        for (spieler in spielerList.indices) {
            val textViewName = TextView(context)
            textViewName.apply {
                text = spielerList[spieler].vorname
                textAlignment = TextView.TEXT_ALIGNMENT_CENTER
            }
            tableRowName.addView(textViewName)
            val textViewPunkte = TextView(context)
            textViewPunkte.apply {
                text = "0"
                textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                extraFutterPunkteFelder.add(this)
            }
            tableRowPunkte.addView(textViewPunkte)
            tablelist.setColumnStretchable(spieler, true)
        }
        tablelist.apply {
            addView(tableRowName)
            addView(tableRowPunkte)
        }
    }
}
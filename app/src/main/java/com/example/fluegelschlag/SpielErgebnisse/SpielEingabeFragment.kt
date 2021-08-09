package com.example.fluegelschlag.SpielErgebnisse


import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fluegelschlag.R
import com.example.fluegelschlag.recyclerAdpater.PunkteArtenAdapter.*
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.spiel_fragment_eingabe.view.*
import javax.inject.Inject

@AndroidEntryPoint
class SpielEingabeFragment : Fragment(R.layout.spiel_fragment_eingabe) {

    @Inject
    lateinit var spielEingabeViewModel: SpielEingabeViewModel

    val punkteUpdateViewModel: PunkteUpdateViewModel = PunkteUpdateViewModel()

    private val vogelSpielEingabeAdapter: VogelEingabeAdapter =
        VogelEingabeAdapter(punkteUpdateViewModel)

    private val bonuskartenSpielEingabeAdapter = BonuskartenEingabeAdapter(punkteUpdateViewModel)

    private val rundenzieleSpielEingabeAdapter = RundenZieleEingabeAdapter(punkteUpdateViewModel)

    private val eierSpielEingabeAdapter = EierEingabeAdapter(punkteUpdateViewModel)

    private val futterSpielEingabeAdapter = FutterEingabeAdapter(punkteUpdateViewModel)

    private val kartenSpielEingabeAdapter = KartenEingabeAdapter(punkteUpdateViewModel)

    val voegelPunkteFelder = arrayListOf<TextView>()
    val bonuskartenPunkteFelder = arrayListOf<TextView>()
    val rundenZielePunkteFelder = arrayListOf<TextView>()
    val eierSpielPunkteFelder = arrayListOf<TextView>()
    val futterSpielPunkteFelder = arrayListOf<TextView>()
    val kartenPunkteFelder = arrayListOf<TextView>()

    val listAdapter: ArrayList<adapterInterface> = arrayListOf()
    lateinit var currentlyOpen: RecyclerView
    lateinit var currentlyDropDown: View
    val listDropDown: ArrayList<View> = ArrayList()
    val listRecyclerView: ArrayList<RecyclerView> = ArrayList()
    var viewSafe: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (viewSafe == null) {
            val v = inflater.inflate(R.layout.spiel_fragment_eingabe, container, false)
            createRecycler(v)
            createLists(v)
            createTables(v)
            createPunkteObserver()
            createOnAuswertenClickListener(v)
            createBackPressCallback()
            startShit(v)
            viewSafe = v
            return v
        } else {
            val v = viewSafe
            return v
        }
    }

    private fun createBackPressCallback() {
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            this.isEnabled = false
            createCustomAlterDialog(
                "Achtung",
                "Einträge wurden noch nicht gespeichert!",
                "Trotzdem Zurück",
                "Stop"
            ) {
                when (it) {
                    true -> {
                        requireActivity().onBackPressed()
                    }
                    false -> this.isEnabled = true
                }
            }
        }
    }


    private fun createOnAuswertenClickListener(view: View) {
        view.spiel_activity_auswerten.setOnClickListener {
            auswerten()
        }
    }

    private fun auswerten() {
        if (pruefeEingabenKorekt()) {
            eingabeBeendet()
        } else {
            createCustomAlterDialog(
                "Achtung",
                "Felder ohne Punkteeintrag gefunden. Felder ohne Eintrag werden als 0 Punkte gewertet.",
                "Weiter",
                "Bearbeiten"
            ) {
                when (it) {
                    true -> eingabeBeendet()
                }
            }
        }

    }

    private fun eingabeBeendet() {
        updatePunkte()
        spielEingabeViewModel.updateGesamtPunkte()
        if (spielEingabeViewModel.gleicheGesamtPunkte().isEmpty()) {
            spielEingabeViewModel.updateRank()
            println("Moved")
            requireActivity().currentFocus?.clearFocus()
            findNavController().navigate(R.id.action_spielEingabeFragment_to_spielErgbenisFragment)
        } else {
            findNavController().navigate(R.id.action_spielEingabeFragment_to_spielZwischenFragment)
        }

    }


    private fun createCustomAlterDialog(
        titel: String,
        message: String,
        yesButton: String,
        noButton: String,
        clickListener: (Boolean) -> (Unit)
    ) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.apply {
            setTitle(titel)
            setMessage(message)
            setPositiveButton(yesButton, DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.dismiss()
                clickListener(true)

            })
            setNegativeButton(noButton, DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.dismiss()
                clickListener(false)
            })
        }
        builder.create()
        builder.show()
    }

    private fun createPunkteObserver() {
        punkteUpdateViewModel.observeAktuelleVogelPunkte { punkte, position ->
            voegelPunkteFelder[position].text = punkte.toString()
        }
        punkteUpdateViewModel.observeBonuskartenPunkte() { punkte, position ->
            bonuskartenPunkteFelder[position].text = punkte.toString()
        }
        punkteUpdateViewModel.observeRundenZielePunkte { punkte, position ->
            rundenZielePunkteFelder[position].text = punkte.toString()
        }
        punkteUpdateViewModel.observeEierPunkte { punkte, position ->
            eierSpielPunkteFelder[position].text = punkte.toString()
        }
        punkteUpdateViewModel.observeFutterPunkte { punkte, position ->
            futterSpielPunkteFelder[position].text = punkte.toString()
        }
        punkteUpdateViewModel.observeKartenPunkte { punkte, position ->
            kartenPunkteFelder[position].text = punkte.toString()
        }
    }

    private fun startShit(v: View) {
        currentlyDropDown = v.spiel_voegel_dropDown
        v.spiel_voegel_list.visibility = View.VISIBLE
        v.spiel_voegel_dropDown.background =
            resources.getDrawable(R.drawable.ic_outline_indeterminate_check_box_24)
        currentlyOpen = v.spiel_voegel_list

        for (i in listAdapter.indices) {
            listAdapter[i].getTheDiffer().submitList(spielEingabeViewModel.aktuellerSpielerList)
            println("List hinzugefügt")
        }

        for (i in listDropDown.indices) {
            listDropDown[i].setOnClickListener {
                if (listRecyclerView[i].visibility == View.GONE) {
                    currentlyOpen.visibility = View.GONE
                    currentlyDropDown.background =
                        resources.getDrawable(R.drawable.ic_twotone_add_box_24)
                    listDropDown[i].background =
                        resources.getDrawable(R.drawable.ic_outline_indeterminate_check_box_24)
                    listRecyclerView[i].visibility = View.VISIBLE
                    currentlyOpen = listRecyclerView[i]
                    currentlyDropDown = listDropDown[i]
                } else if (listRecyclerView[i].visibility == View.VISIBLE) {
                    currentlyDropDown.background =
                        resources.getDrawable(R.drawable.ic_twotone_add_box_24)
                    currentlyOpen.visibility = View.GONE
                }
            }
        }


    }

    fun pruefeEingabenKorekt(): Boolean {
        val editTextFields = arrayListOf(
            *vogelSpielEingabeAdapter.getEditTextViews().toTypedArray(),
            *bonuskartenSpielEingabeAdapter.getEditTextViews().toTypedArray(),
            *rundenzieleSpielEingabeAdapter.getEditTextViews().toTypedArray(),
            *eierSpielEingabeAdapter.getEditTextViews().toTypedArray(),
            *futterSpielEingabeAdapter.getEditTextViews().toTypedArray(),
            *kartenSpielEingabeAdapter.getEditTextViews().toTypedArray()
        )

        currentlyOpen.visibility = View.VISIBLE

        for (i in editTextFields.indices) {
            if (editTextFields[i].first.text == null) {
                return false
            } else if (editTextFields[i].first.text.toString().isEmpty()) {
                return false
            }
        }
        return true
    }

    fun updatePunkte() {
        for (i in vogelSpielEingabeAdapter.getEditTextViews().indices) {
            if (checkifEmpty(vogelSpielEingabeAdapter.getEditTextViews()[i].first)) {
                spielEingabeViewModel.updateVogelPunkte(
                    0,
                    vogelSpielEingabeAdapter.getEditTextViews()[i].second
                )
            } else {
                spielEingabeViewModel.updateVogelPunkte(
                    vogelSpielEingabeAdapter.getEditTextViews()[i].first.text.toString().toInt(),
                    vogelSpielEingabeAdapter.getEditTextViews()[i].second
                )
            }
        }
        for (i in bonuskartenSpielEingabeAdapter.getEditTextViews().indices) {
            if (checkifEmpty(bonuskartenSpielEingabeAdapter.getEditTextViews()[i].first)) {
                spielEingabeViewModel.updateBonuskartenPunkte(
                    0,
                    bonuskartenSpielEingabeAdapter.getEditTextViews()[i].second
                )
            } else {
                spielEingabeViewModel.updateBonuskartenPunkte(
                    bonuskartenSpielEingabeAdapter.getEditTextViews()[i].first.text.toString()
                        .toInt(),
                    bonuskartenSpielEingabeAdapter.getEditTextViews()[i].second
                )

            }

        }
        for (i in rundenzieleSpielEingabeAdapter.getEditTextViews().indices) {
            if (checkifEmpty(rundenzieleSpielEingabeAdapter.getEditTextViews()[i].first)) {
                spielEingabeViewModel.updateRundenzielePunkte(
                    0,
                    rundenzieleSpielEingabeAdapter.getEditTextViews()[i].second
                )
            } else {
                spielEingabeViewModel.updateRundenzielePunkte(
                    rundenzieleSpielEingabeAdapter.getEditTextViews()[i].first.text.toString()
                        .toInt(),
                    rundenzieleSpielEingabeAdapter.getEditTextViews()[i].second
                )
            }

        }
        for (i in eierSpielEingabeAdapter.getEditTextViews().indices) {
            if (checkifEmpty(eierSpielEingabeAdapter.getEditTextViews()[i].first)) {
                spielEingabeViewModel.updateEierPunkte(
                    0,
                    eierSpielEingabeAdapter.getEditTextViews()[i].second
                )
            } else {
                spielEingabeViewModel.updateEierPunkte(
                    eierSpielEingabeAdapter.getEditTextViews()[i].first.text.toString().toInt(),
                    eierSpielEingabeAdapter.getEditTextViews()[i].second
                )
            }

        }
        for (i in futterSpielEingabeAdapter.getEditTextViews().indices) {
            if (checkifEmpty(futterSpielEingabeAdapter.getEditTextViews()[i].first)) {
                spielEingabeViewModel.updateFutterPunkte(
                    0,
                    futterSpielEingabeAdapter.getEditTextViews()[i].second
                )
            } else {
                spielEingabeViewModel.updateFutterPunkte(
                    futterSpielEingabeAdapter.getEditTextViews()[i].first.text.toString().toInt(),
                    futterSpielEingabeAdapter.getEditTextViews()[i].second
                )
            }

        }
        for (i in kartenSpielEingabeAdapter.getEditTextViews().indices) {
            println("Es gibt kartenPunkte")
            if (checkifEmpty(kartenSpielEingabeAdapter.getEditTextViews()[i].first)) {
                spielEingabeViewModel.updateKartenPunkte(
                    0,
                    kartenSpielEingabeAdapter.getEditTextViews()[i].second
                )
            } else {
                spielEingabeViewModel.updateKartenPunkte(
                    kartenSpielEingabeAdapter.getEditTextViews()[i].first.text.toString().toInt(),
                    kartenSpielEingabeAdapter.getEditTextViews()[i].second
                )
            }

        }
        println("GesamtPunkteUpdated")
    }

    private fun checkifEmpty(text: TextInputEditText): Boolean {
        if (text.text == null) {
            return true
        } else if (text.text.toString().isEmpty()) {
            return true
        }

        return false
    }

    private fun createLists(view: View) {
        listRecyclerView.add(view.spiel_voegel_list)
        listRecyclerView.add(view.spiel_bonuskarten_list)
        listRecyclerView.add(view.spiel_rundenziele_list)
        listRecyclerView.add(view.spiel_eier_list)
        listRecyclerView.add(view.spiel_futter_list)
        listRecyclerView.add(view.spiel_karten_list)

        listDropDown.add(view.spiel_voegel_dropDown)
        listDropDown.add(view.spiel_bonuskarten_dropDown)
        listDropDown.add(view.spiel_rundenziele_dropDown)
        listDropDown.add(view.spiel_eier_dropDown)
        listDropDown.add(view.spiel_futter_dropDown)
        listDropDown.add(view.spiel_karten_dropDown)

        listAdapter.add(vogelSpielEingabeAdapter)
        listAdapter.add(bonuskartenSpielEingabeAdapter)
        listAdapter.add(rundenzieleSpielEingabeAdapter)
        listAdapter.add(eierSpielEingabeAdapter)
        listAdapter.add(futterSpielEingabeAdapter)
        listAdapter.add(kartenSpielEingabeAdapter)
    }

    private fun createTables(view: View) {
        val tablelist = arrayListOf<TableLayout>(
            view.spiel_voegel_table,
            view.spiel_bonuskarten_table,
            view.spiel_rundenziele_table,
            view.spiel_eier_table,
            view.spiel_futter_table,
            view.spiel_karten_table
        )
        val spielerList = spielEingabeViewModel.aktuellerSpielerList
        for (table in tablelist.indices) {
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
                    when (table) {
                        0 -> voegelPunkteFelder.add(this)
                        1 -> bonuskartenPunkteFelder.add(this)
                        2 -> rundenZielePunkteFelder.add(this)
                        3 -> eierSpielPunkteFelder.add(this)
                        4 -> futterSpielPunkteFelder.add(this)
                        5 -> kartenPunkteFelder.add(this)
                    }

                }
                tableRowPunkte.addView(textViewPunkte)
                tablelist[table].setColumnStretchable(spieler, true)
            }
            tablelist[table].apply {
                addView(tableRowName)
                addView(tableRowPunkte)

            }
        }
    }

    private fun createRecycler(view: View) {
        view.spiel_voegel_list.apply {
            adapter = vogelSpielEingabeAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(false)
        }
        view.spiel_bonuskarten_list.apply {
            adapter = bonuskartenSpielEingabeAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(false)
        }
        view.spiel_rundenziele_list.apply {
            adapter = rundenzieleSpielEingabeAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(false)
        }
        view.spiel_eier_list.apply {
            adapter = eierSpielEingabeAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(false)
        }
        view.spiel_futter_list.apply {
            adapter = futterSpielEingabeAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(false)
        }
        view.spiel_karten_list.apply {
            adapter = kartenSpielEingabeAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(false)
        }


    }


}
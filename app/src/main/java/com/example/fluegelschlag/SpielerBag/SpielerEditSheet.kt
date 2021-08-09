package com.example.fluegelschlag.SpielerBag

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.example.fluegelschlag.Entities.Spieler
import com.example.fluegelschlag.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.spieler_add_sheet.view.*
import kotlinx.android.synthetic.main.spieler_edit_sheet.view.*

@AndroidEntryPoint
class SpielerEditSheet(spieler: Spieler) : BottomSheetDialogFragment(){

    private val spieler = Spieler(spieler.vorname,spieler.nachname, spieler.koennen)
    private val spielerViewModel: SpielerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.spieler_edit_sheet, container, false)

        //TODO {"Bild anzeigen mit image view = image"}
        val modalBottomSheetBehavior = (this.dialog as BottomSheetDialog).behavior

        v.spieler_edit_sheet_name.text = spieler.vorname + " " + spieler.nachname
        when(spieler.koennen) {
            "Anfänger" -> v.spieler_edit_fortschritt_toggleGroup.check(R.id.spieler_edit_fortschritt_anfang)
            "Fortgeschritten" -> v.spieler_edit_fortschritt_toggleGroup.check(R.id.spieler_edit_fortschritt_mittel)
            "Profi" -> v.spieler_edit_fortschritt_toggleGroup.check(R.id.spieler_edit_fortschritt_profi)
        }

        val defaultCheck = v.spieler_edit_fortschritt_toggleGroup.checkedButtonId

        v.spieler_edit_fortschritt_toggleGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
            v.spieler_edit_speichern.isEnabled = checkedId != defaultCheck
        }

        v.spieler_edit_speichern.setOnClickListener {
            when(v.spieler_edit_fortschritt_toggleGroup.checkedButtonId) {
                v.spieler_edit_fortschritt_anfang.id -> spieler.koennen = v.spieler_edit_fortschritt_anfang.text.toString()
                v.spieler_edit_fortschritt_mittel.id -> spieler.koennen = v.spieler_edit_fortschritt_mittel.text.toString()
                v.spieler_edit_fortschritt_profi.id -> spieler.koennen = v.spieler_edit_fortschritt_profi.text.toString()
            }
            spielerViewModel.updateSpieler(spieler)

            this.dismiss()
        }

        v.spieler_edit_delete.setOnClickListener {
            v.spieler_edit_achtung_text.visibility = View.VISIBLE
            v.spieler_edit_achtung_trotzdemloeschen.visibility = View.VISIBLE
        }

        v.spieler_edit_achtung_trotzdemloeschen.setOnClickListener {
            spielerViewModel.deleteSingleSpieler(spieler)
            this.dismiss()
        }

        return v
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (dialog as BottomSheetDialog).behavior.isHideable = true


    }
}
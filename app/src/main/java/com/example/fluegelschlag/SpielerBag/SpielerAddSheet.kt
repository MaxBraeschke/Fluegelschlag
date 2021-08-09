package com.example.fluegelschlag.SpielerBag

import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.example.fluegelschlag.Entities.Spiel
import com.example.fluegelschlag.Entities.Spieler
import com.example.fluegelschlag.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.spieler_add_sheet.view.*

@AndroidEntryPoint
class SpielerAddSheet : BottomSheetDialogFragment(){

    private val spielerViewModel: SpielerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.spieler_add_sheet, container, false)

        //TODO {"Bild anzeigen mit image view = image"}
        val modalBottomSheetBehavior = (this.dialog as BottomSheetDialog).behavior

        /*v.spieler_input_name.filters = v.spieler_input_name.filters.let {
            it + InputFilter { source, _, _, _, _, _ ->
                source.filterNot { char -> char.isWhitespace() }
            }
        }

        v.spieler_input_nachname.filters = v.spieler_input_nachname.filters.let {
            it + InputFilter { source, _, _, _, _, _ ->
                source.filterNot { char -> char.isWhitespace() }
            }
        }*/

        v.spieler_input_name.addTextChangedListener {
            checkFinish(v)
        }
        v.spieler_input_nachname.addTextChangedListener {
            checkFinish(v)
        }
        v.spieler_fortschritt_toggleGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
            checkFinish(v)
        }

        v.spieler_add_finish.setOnClickListener {
            var fortschritt = ""
            when(v.spieler_fortschritt_toggleGroup.checkedButtonId) {
                v.spieler_fortschritt_anfang.id -> fortschritt = v.spieler_fortschritt_anfang.text.toString()
                v.spieler_fortschritt_mittel.id -> fortschritt = v.spieler_fortschritt_mittel.text.toString()
                v.spieler_fortschritt_profi.id -> fortschritt = v.spieler_fortschritt_profi.text.toString()
            }

            spielerViewModel.insertSpieler(Spieler(v.spieler_input_name.text.toString(), v.spieler_input_nachname.text.toString(), fortschritt))

            this.dismiss()
        }

        return v
    }

    private fun checkFinish(v: View) {
        v.spieler_add_finish.isEnabled = v.spieler_input_name.text.toString().isNotEmpty() && v.spieler_fortschritt_toggleGroup.checkedButtonId != -1 &&
                v.spieler_input_nachname.text.toString().isNotEmpty()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (dialog as BottomSheetDialog).behavior.isHideable = true
    }
}
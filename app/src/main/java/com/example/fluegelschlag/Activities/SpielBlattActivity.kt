package com.example.fluegelschlag.Activities

import android.app.Activity
import android.content.DialogInterface
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.fluegelschlag.R
import com.example.fluegelschlag.SpielErgebnisse.SpielEingabeFragment
import com.example.fluegelschlag.SpielErgebnisse.SpielEingabeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.home_addgame_sheet.*
import kotlinx.android.synthetic.main.spiel_activity.*
import javax.inject.Inject

@AndroidEntryPoint
class SpielBlattActivity : AppCompatActivity() {

    @Inject
    lateinit var spielEingabeViewModel: SpielEingabeViewModel

    lateinit var listener: SpielEingabeFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.spiel_activity)

        findNavController(spielergebniss_nav_host.id).addOnDestinationChangedListener { controller, destination, arguments ->
            val keyboard : InputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            val v = currentFocus
            v?.clearFocus()
            keyboard.hideSoftInputFromWindow(v?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }

    }




    override fun onDestroy() {
        super.onDestroy()
        spielEingabeViewModel.clearLists()
    }

}
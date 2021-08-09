package com.example.fluegelschlag.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.fluegelschlag.R
import com.example.fluegelschlag.SpielErgebnisse.SpielEingabeViewModel
import com.example.fluegelschlag.SpielerBag.SpielerViewModel
import com.example.fluegelschlag.homeBag.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

     val homeViewModel: HomeViewModel by viewModels()

     val spielerViewModel: SpielerViewModel by viewModels()
    @Inject
    lateinit var spielErgebnisseViewModel: SpielEingabeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        homeViewModel.getAlleSpiele().observe(this, Observer {
            for (i in it.indices)
            println(it[i])
        })
        spielerViewModel.getAlleSpieler().observe(this, Observer {
            for (i in it.indices) {
                println(it[i])
            }
        })
        spielErgebnisseViewModel.getAlleSpielErgebnisse().observe(this, Observer {
            for (i in it.indices) {
                println(it[i])
            }
        })
        bottomNavigation.setupWithNavController(nav_host_fragment_container.findNavController())
    }
}
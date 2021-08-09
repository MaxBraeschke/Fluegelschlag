package com.example.fluegelschlag

import android.content.Context
import androidx.room.Room
import com.example.fluegelschlag.recyclerAdpater.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideNutritionDatabase(@ApplicationContext app: Context) = Room.databaseBuilder(app, Datenbank::class.java, "FluegelschlagDatenbank").fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideUserDao(db : Datenbank) = db.getDao()

    @Singleton
    @Provides
    fun provideSpielDao(db : Datenbank) = db.getSpielDao()

    @Singleton
    @Provides
    fun provideSpielErgebnisseDao(db : Datenbank) = db.getSpielErgebnisseDao()

    @Singleton
    @Provides
    fun provideBestenliste() = BestenlisteAdapter()

    @Singleton
    @Provides
    fun provideLetzteSpieleListe() = StatistikSpieleAdapter()

    @Singleton
    @Provides
    fun provideSpielerListe() = SpielerListeAdpter()

    @Singleton
    @Provides
    fun provideAddGameAddSpielerListe() = AddGameAddSpielerAdapter()




}
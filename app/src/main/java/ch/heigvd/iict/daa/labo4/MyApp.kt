package ch.heigvd.iict.daa.labo4

import android.app.Application
import ch.heigvd.iict.daa.labo4.data.AppDatabase

class MyApp : Application() {
    val repository by lazy {
        val database = AppDatabase.getInstance(this)
        Repository(database.noteDao())
    }
}
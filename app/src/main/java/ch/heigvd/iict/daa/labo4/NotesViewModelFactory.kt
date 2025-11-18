package ch.heigvd.iict.daa.labo4

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NotesViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NotesViewModel::class.java)) {
            return NotesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
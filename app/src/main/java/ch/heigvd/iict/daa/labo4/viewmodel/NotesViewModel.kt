package ch.heigvd.iict.daa.labo4.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import ch.heigvd.iict.daa.labo4.Repository
import ch.heigvd.iict.daa.labo4.models.Note
import ch.heigvd.iict.daa.labo4.models.NoteAndSchedule
import kotlin.random.Random

class NotesViewModel(private val repository: Repository) : ViewModel() {
    private val sortOrder = MutableLiveData(SortOrder.BY_CREATION)

    //: LiveData<List<NoteAndSchedule>>
    val sortedNotes: LiveData<List<NoteAndSchedule>> = sortOrder.switchMap { order ->
        when (order) {
            SortOrder.BY_CREATION -> repository.allNotesByCreatedDesc
            SortOrder.BY_DUE_DATE -> repository.allNotesByDueDateAsc
        }
    }
    val countNotes = repository.countNotes //: LiveData<Long>

    fun setSortOrder(order: SortOrder): Boolean {
        if (sortOrder.value != order) sortOrder.value = order
        return true
    }

    /* création d’une Note aléatoire et insertion dans base de données */
    fun generateANote() {
        val note = Note.generateRandomNote()
        val schedule =
            if (Random.nextBoolean()) Note.generateRandomSchedule() else null
        repository.insertNoteWithSchedule(note, schedule)
    }

    /* suppression de toutes les Notes de la base de données */
    fun deleteAllNote() = repository.deleteAll()

    enum class SortOrder {
        BY_CREATION,
        BY_DUE_DATE
    }
}
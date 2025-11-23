package ch.heigvd.iict.daa.labo4.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import ch.heigvd.iict.daa.labo4.Repository
import ch.heigvd.iict.daa.labo4.models.Note
import ch.heigvd.iict.daa.labo4.models.NoteAndSchedule

class NotesViewModel(
    private val repository: Repository,
    context: Context
) : ViewModel() {

    private val prefs: SharedPreferences = context.getSharedPreferences(
        "notes_prefs",
        Context.MODE_PRIVATE
    )

    private val sortOrder = MutableLiveData(loadSortOrder())

    val allNotes = repository.allNotes //: LiveData<List<NoteAndSchedule>>

    //: LiveData<List<NoteAndSchedule>>
    val sortedNotes: LiveData<List<NoteAndSchedule>> = sortOrder.switchMap { order ->
        when (order) {
            SortOrder.BY_CREATION -> repository.allNotesByCreatedDesc
            SortOrder.BY_DUE_DATE -> repository.allNotesByDueDateAsc
        }
    }
    val countNotes = repository.countNotes //: LiveData<Long>

    /* sélection de l'ordre de tri */
    fun setSortOrder(order: SortOrder): Boolean {
        if (sortOrder.value != order) {
            sortOrder.value = order
            saveSortOrder(order)
        }
        return true
    }

    /* création d’une Note aléatoire et insertion dans base de données */
    fun generateANote() {
        val note = Note.generateRandomNote()
        val schedule = Note.generateRandomSchedule()
        repository.insertNoteWithSchedule(note, schedule)
    }

    /* suppression de toutes les Notes de la base de données */
    fun deleteAllNote() = repository.deleteAll()

    private fun loadSortOrder(): SortOrder {
        val orderName = prefs.getString("sort_order", SortOrder.BY_CREATION.name)
        return SortOrder.valueOf(orderName ?: SortOrder.BY_CREATION.name)
    }

    private fun saveSortOrder(order: SortOrder) {
        prefs.edit {
            putString("sort_order", order.name)
        }
    }

    enum class SortOrder {
        BY_CREATION,
        BY_DUE_DATE
    }
}
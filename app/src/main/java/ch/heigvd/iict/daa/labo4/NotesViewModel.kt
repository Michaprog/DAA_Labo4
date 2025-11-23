package ch.heigvd.iict.daa.labo4

import androidx.lifecycle.ViewModel
import ch.heigvd.iict.daa.labo4.models.Note

class NotesViewModel(private val repository: Repository) : ViewModel() {
    val allNotes = repository.allNotesByCreatedDesc //: LiveData<List<NoteAndSchedule>>
    val countNotes = repository.countNotes //: LiveData<Long>

    fun generateANote() {
        /* création d’une Note aléatoire et insertion dans base de données */
        val note = Note.generateRandomNote()
        val schedule = if (kotlin.random.Random.nextBoolean()) Note.generateRandomSchedule() else null
        repository.insertNoteWithSchedule(note, schedule)
    }

    fun deleteAllNote() {
        /* suppression de toutes les Notes de la base de données */
        repository.deleteAll()
    }
}

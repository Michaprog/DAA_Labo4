package ch.heigvd.iict.daa.labo4

import android.util.Log
import ch.heigvd.iict.daa.labo4.dao.NoteDao
import ch.heigvd.iict.daa.labo4.models.Note
import ch.heigvd.iict.daa.labo4.models.Schedule
import kotlin.concurrent.thread

class Repository(private val noteDao: NoteDao) {
    val allNotesByCreatedDesc = noteDao.getAllByCreatedDesc() //: LiveData<List<NoteAndSchedule>>
    val allNotesByDueDateAsc = noteDao.getAllByDueDateAsc() //: LiveData<List<NoteAndSchedule>>
    val countNotes = noteDao.countNotes() //: LiveData<Long>

    fun insertNoteWithSchedule(note: Note, schedule: Schedule?) {
        thread {
            noteDao.insertNoteWithSchedule(note, schedule)
            Log.d("Repository", "Inserted note=${note}")
        }
    }

    fun deleteAll() {
        thread {
            noteDao.deleteAll()
        }
    }
}
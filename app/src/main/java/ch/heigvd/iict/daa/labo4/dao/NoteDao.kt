// ch/heigvd/iict/daa/labo4/data/NoteDao.kt
package ch.heigvd.iict.daa.labo4.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ch.heigvd.iict.daa.labo4.models.*

@Dao
interface NoteDao {

    // All notes sorted by creation date (newest first)
    @Transaction
    @Query("SELECT * FROM Note ORDER BY creationDate DESC")
    fun getAllByCreatedDesc(): LiveData<List<NoteAndSchedule>>

    // All notes sorted by due date (Schedule)
    @Transaction
    @Query("""
        SELECT Note.* FROM Note 
        LEFT JOIN Schedule ON Note.noteId = Schedule.ownerId 
        ORDER BY 
            (Schedule.date IS NULL) ASC, 
            Schedule.date ASC
    """)
    fun getAllByDueDateAsc(): LiveData<List<NoteAndSchedule>>

    // Count total notes
    @Query("SELECT COUNT(*) FROM Note")
    fun countNotes(): LiveData<Long>

    // Count total notes direct
    @Query("SELECT COUNT(*) FROM Note")
    fun countNotesDirect() : Long

    // Insert new Note
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note: Note): Long

    // Insert Schedule linked to Note
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSchedule(schedule: Schedule): Long

    // Helper: insert both
    @Transaction
    fun insertNoteWithSchedule(note: Note, schedule: Schedule?) {
        val noteId = insertNote(note)
        schedule?.let { insertSchedule(it.copy(ownerId = noteId)) }
    }

    // Delete everything
    @Query("DELETE FROM Schedule")
    fun deleteAllSchedules()

    @Query("DELETE FROM Note")
    fun deleteAllNotes()

    @Transaction
    fun deleteAll() {
        deleteAllSchedules()
        deleteAllNotes()
    }
}
// ch/heigvd/iict/daa/labo4/data/NoteDao.kt
package ch.heigvd.iict.daa.labo4.data

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
    fun countNotes(): LiveData<Int>

    // Insert new Note
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note): Long

    // Insert Schedule linked to Note
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchedule(schedule: Schedule): Long

    // Helper: insert both
    @Transaction
    suspend fun insertNoteWithSchedule(note: Note, schedule: Schedule?) {
        val noteId = insertNote(note)
        schedule?.let { insertSchedule(it.copy(ownerId = noteId)) }
    }

    // Delete everything
    @Query("DELETE FROM Schedule")
    suspend fun deleteAllSchedules()

    @Query("DELETE FROM Note")
    suspend fun deleteAllNotes()

    @Transaction
    suspend fun deleteAll() {
        deleteAllSchedules()
        deleteAllNotes()
    }
}
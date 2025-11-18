// ch/heigvd/iict/daa/labo4/data/AppDatabase.kt
package ch.heigvd.iict.daa.labo4.data

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import ch.heigvd.iict.daa.labo4.converters.Converters
import ch.heigvd.iict.daa.labo4.dao.NoteDao
import ch.heigvd.iict.daa.labo4.models.*
import java.util.*
import kotlin.concurrent.thread

@Database(
    entities = [Note::class, Schedule::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "notes_database"
                )
                    .addCallback(SeedDatabaseCallback())
                    .build()
                INSTANCE = instance
                instance
            }
    }

    private class SeedDatabaseCallback() : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                thread {
                    val isEmpty = database.noteDao().countNotesDirect() == 0L
                    if (isEmpty) {
                        // Peupler DB a la 1ere creation (async)
                        populate(database.noteDao())
                    }
                }
            }
        }
    }
}

private fun populate(dao: NoteDao) {
    dao.deleteAll()
    repeat(5) {
        val note = Note.generateRandomNote()
        val schedule = if (Random().nextBoolean()) Note.generateRandomSchedule() else null
        dao.insertNoteWithSchedule(note, schedule)
    }
}
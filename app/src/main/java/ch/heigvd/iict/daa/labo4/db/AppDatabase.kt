// ch/heigvd/iict/daa/labo4/data/AppDatabase.kt
package ch.heigvd.iict.daa.labo4.data

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import ch.heigvd.iict.daa.labo4.models.*
import kotlinx.coroutines.*
import java.util.*

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

        fun getInstance(context: Context, scope: CoroutineScope): AppDatabase =
            INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "notes_database"
                )
                    .addCallback(SeedDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
    }

    private class SeedDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) { populate(database.noteDao()) }
            }
        }
    }
}

suspend fun populate(dao: NoteDao) {
    dao.deleteAll()
    repeat(5) {
        val note = Note.generateRandomNote()
        val schedule = if (Random().nextBoolean()) Note.generateRandomSchedule() else null
        dao.insertNoteWithSchedule(note, schedule)
    }
}
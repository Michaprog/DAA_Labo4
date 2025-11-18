// ch/heigvd/iict/daa/labo4/data/Converters.kt
package ch.heigvd.iict.daa.labo4.converters

import androidx.room.TypeConverter
import ch.heigvd.iict.daa.labo4.models.State
import ch.heigvd.iict.daa.labo4.models.Type
import java.util.*

class Converters {

    // Calendar <-> Long
    @TypeConverter
    fun fromCalendarToLong(value: Calendar?): Long? = value?.timeInMillis

    @TypeConverter
    fun fromLongToCalendar(value: Long?): Calendar? =
        value?.let { Calendar.getInstance().apply { timeInMillis = it } }

    // Enum State <-> String
    @TypeConverter
    fun fromStateToString(value: State?): String? = value?.name

    @TypeConverter
    fun fromStringToState(value: String?): State? =
        value?.let { State.valueOf(it) }

    // Enum Type <-> String
    @TypeConverter
    fun fromTypeToString(value: Type?): String? = value?.name

    @TypeConverter
    fun fromStringToType(value: String?): Type? =
        value?.let { Type.valueOf(it) }
}
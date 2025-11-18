package ch.heigvd.iict.daa.labo4.placeholder

import java.util.ArrayList
import java.util.HashMap

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object PlaceholderContent {

    /**
     * An array of sample (placeholder) items.
     */
    val NOTES: MutableList<PlaceholderNote> = ArrayList()

    /**
     * A map of sample (placeholder) items, by ID.
     */
    val NOTE_MAP: MutableMap<String, PlaceholderNote> = HashMap()

    private val COUNT = 25

    init {
        // Add some sample items.
        for (i in 1..COUNT) {
            addItem(createPlaceholderItem(i))
        }
    }

    private fun addItem(note: PlaceholderNote) {
        NOTES.add(note)
        NOTE_MAP.put(note.id, note)
    }

    private fun createPlaceholderItem(position: Int): PlaceholderNote {
        return PlaceholderNote(position.toString(), "Note " + position, makeDetails(position))
    }

    private fun makeDetails(position: Int): String {
        val builder = StringBuilder()
        builder.append("Details about Note: ").append(position)
        for (i in 0..position - 1) {
            builder.append("\nMore details information here.")
        }
        return builder.toString()
    }

    /**
     * A placeholder note representing a piece of content.
     */
    data class PlaceholderNote(val id: String, val content: String, val details: String) {
        override fun toString(): String = content
    }
}
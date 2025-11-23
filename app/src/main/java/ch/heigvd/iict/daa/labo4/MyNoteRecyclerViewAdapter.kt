package ch.heigvd.iict.daa.labo4

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ch.heigvd.iict.daa.labo4.models.NoteAndSchedule
import ch.heigvd.iict.daa.labo4.models.State

class MyNoteRecyclerViewAdapter :
    RecyclerView.Adapter<MyNoteRecyclerViewAdapter.ViewHolder>() {

    companion object {
        private const val VIEW_SIMPLE = 0
        private const val VIEW_WITH_SCHEDULE = 1
    }

    private val differ = AsyncListDiffer(this, NoteDiffCallback())

    var values: List<NoteAndSchedule>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    fun updateNotes(newValues: List<NoteAndSchedule>) {
        values = newValues
    }

    override fun getItemViewType(position: Int): Int {
        val noteAndSchedule = values[position]
        return if (noteAndSchedule.schedule == null) VIEW_SIMPLE else VIEW_WITH_SCHEDULE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutId = when (viewType) {
            VIEW_WITH_SCHEDULE -> R.layout.fragment_note_with_schedule
            else -> R.layout.fragment_note_simple
        }

        val view = LayoutInflater.from(parent.context)
            .inflate(layoutId, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val noteAndSchedule = values[position]
        val note = noteAndSchedule.note
        val schedule = noteAndSchedule.schedule

        holder.title.text = note.title
        holder.body.text = note.text
        holder.type.text = note.type.name
        holder.state.text = note.state.name

        // date de création
        holder.created.text = note.creationDate.time.toString()

        // date d’échéance (si Schedule présent)
        if (schedule != null) {
            holder.due.visibility = View.VISIBLE
            holder.due.text = schedule.date.time.toString()
        } else {
            holder.due.visibility = View.GONE
        }

        // Couleur selon State (on garde ça, c'est cheap et utile)
        val context = holder.itemView.context
        val colorRes = when (note.state) {
            State.IN_PROGRESS -> android.R.color.holo_orange_dark
            State.DONE        -> android.R.color.holo_green_dark
        }
        val color = ContextCompat.getColor(context, colorRes)
        holder.state.setTextColor(color)
    }

    override fun getItemCount(): Int = values.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.text_title)
        val body: TextView = itemView.findViewById(R.id.text_body)
        val type: TextView = itemView.findViewById(R.id.text_type)
        val state: TextView = itemView.findViewById(R.id.text_state)
        val created: TextView = itemView.findViewById(R.id.text_created)
        val due: TextView = itemView.findViewById(R.id.text_due)
    }

    private class NoteDiffCallback : DiffUtil.ItemCallback<NoteAndSchedule>() {
        override fun areItemsTheSame(oldItem: NoteAndSchedule, newItem: NoteAndSchedule): Boolean {
            return oldItem.note.noteId == newItem.note.noteId
        }

        override fun areContentsTheSame(oldItem: NoteAndSchedule, newItem: NoteAndSchedule): Boolean {
            return oldItem == newItem
        }
    }
}
package ch.heigvd.iict.daa.labo4

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ch.heigvd.iict.daa.labo4.databinding.FragmentNoteBinding
import ch.heigvd.iict.daa.labo4.models.NoteAndSchedule

class MyNoteRecyclerViewAdapter : RecyclerView.Adapter<MyNoteRecyclerViewAdapter.ViewHolder>() {

    private val differ = AsyncListDiffer(this, NoteDiffCallback())

    var values: List<NoteAndSchedule>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    fun updateNotes(newValues: List<NoteAndSchedule>) {
        values = newValues
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentNoteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val noteAndSchedule = values[position]
        val note = noteAndSchedule.note

        holder.idView.text = note.noteId.toString()
        holder.contentView.text = note.title
    }

    override fun getItemCount(): Int = values.size

    class ViewHolder(binding: FragmentNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content
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
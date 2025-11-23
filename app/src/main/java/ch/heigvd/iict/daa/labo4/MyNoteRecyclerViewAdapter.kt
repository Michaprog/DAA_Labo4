package ch.heigvd.iict.daa.labo4

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ch.heigvd.iict.daa.labo4.databinding.FragmentNoteBinding
import ch.heigvd.iict.daa.labo4.models.NoteAndSchedule

class MyNoteRecyclerViewAdapter(
    private var values: List<NoteAndSchedule>
) : RecyclerView.Adapter<MyNoteRecyclerViewAdapter.ViewHolder>() {

    fun updateNotes(newValues: List<NoteAndSchedule>) {
        values = newValues
        // For the lab this is fine; in a real app you'd use DiffUtil.
        notifyDataSetChanged()
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
        holder.contentView.text = note.title      // change to whatever field you want to show
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content
    }
}
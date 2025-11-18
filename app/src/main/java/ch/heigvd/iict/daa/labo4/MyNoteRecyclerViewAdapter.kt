package ch.heigvd.iict.daa.labo4

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView

import ch.heigvd.iict.daa.labo4.placeholder.PlaceholderContent.PlaceholderNote
import ch.heigvd.iict.daa.labo4.databinding.FragmentNoteBinding

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderNote].
 * TODO: Replace the implementation with code for your data type.
 */
class MyNoteRecyclerViewAdapter(
    private val values: List<PlaceholderNote>
) : RecyclerView.Adapter<MyNoteRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentNoteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = values[position]
        holder.idView.text = note.id
        holder.contentView.text = note.content
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}
package ch.heigvd.iict.daa.labo4.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.heigvd.iict.daa.labo4.MyApp
import ch.heigvd.iict.daa.labo4.MyNoteRecyclerViewAdapter
import ch.heigvd.iict.daa.labo4.NotesViewModel
import ch.heigvd.iict.daa.labo4.NotesViewModelFactory
import ch.heigvd.iict.daa.labo4.R

class NotesFragment : Fragment() {

    private var columnCount = 1

    private val viewModel: NotesViewModel by activityViewModels {
        val app = requireActivity().application as MyApp
        NotesViewModelFactory(app.repository)
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyNoteRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_note_list, container, false)
        recyclerView = view.findViewById(R.id.fragment_note_list)

        recyclerView.layoutManager =
            if (columnCount <= 1) LinearLayoutManager(context)
            else GridLayoutManager(context, columnCount)

        adapter = MyNoteRecyclerViewAdapter(emptyList())
        recyclerView.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // observer avec viewLifecycleOwner, pas 'this'
        viewModel.allNotes.observe(viewLifecycleOwner) { notes ->
            adapter.updateNotes(notes)
        }
    }

    companion object {
        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) =
            NotesFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}
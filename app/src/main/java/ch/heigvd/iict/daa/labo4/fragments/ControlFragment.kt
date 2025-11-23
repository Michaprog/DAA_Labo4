package ch.heigvd.iict.daa.labo4.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ch.heigvd.iict.daa.labo4.MyApp
import ch.heigvd.iict.daa.labo4.R
import ch.heigvd.iict.daa.labo4.viewmodel.NotesViewModel
import ch.heigvd.iict.daa.labo4.viewmodel.NotesViewModelFactory
import com.google.android.material.button.MaterialButton

class ControlFragment : Fragment() {

    // shared ViewModel with the Activity (and NotesFragment)
    private val viewModel: NotesViewModel by activityViewModels {
        val app = requireActivity().application as MyApp
        NotesViewModelFactory(app.repository, requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_control, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val generateBtn = view.findViewById<MaterialButton>(R.id.button_generate)
        val deleteBtn = view.findViewById<MaterialButton>(R.id.button_delete)
        val countText = view.findViewById<TextView>(R.id.text_count)

        // buttons -> ViewModel
        generateBtn.setOnClickListener { viewModel.generateANote() }
        deleteBtn.setOnClickListener { viewModel.deleteAllNote() }

        // observe count and update label
        viewModel.countNotes.observe(viewLifecycleOwner) { count ->
            countText.text = "Notes : $count"
        }
    }
}
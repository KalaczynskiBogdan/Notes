package com.example.notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.notes.databinding.FragmentNotesBinding

class NotesFragment : Fragment() {
    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!
    private val dataBase: DataBase = DataBase.getInstance()
    private var notesAdapter: NotesAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (dataBase.getList().isEmpty()) {
            dataBase.makeList()
        } else (dataBase.getList())
        notesAdapter = NotesAdapter()

        notesAdapter?.setOnNoteClickListener(object : NotesAdapter.OnNoteClickListener {
            override fun onNoteClick(note: Note) {
                val fragment = InfoFragment.newInstance(
                    note.id,
                    note.text,
                    note.image
                )
                (activity as MainActivity).navigateToNextScreen(fragment)
            }
        })

        binding.buttonAddNote.setOnClickListener {
            val fragment = AddNoteFragment()
            (activity as MainActivity).navigateToNextScreen(fragment)
        }
        binding.rvNotes.adapter = notesAdapter
    }

    override fun onResume() {
        super.onResume()
        showNotes()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun showNotes() {
        notesAdapter?.setListOfNotes(dataBase.getList())
    }
}
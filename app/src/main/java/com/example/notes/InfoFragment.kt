package com.example.notes


import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import com.example.notes.databinding.FragmentInfoBinding

class InfoFragment : Fragment() {
    private var id: Int? = null
    private var text: String? = null
    private var image: String? = null
    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showInfo()
    }

    private fun showInfo() {
        arguments?.let {
            id = it.getInt(NOTE_ID)
            text = it.getString(NOTE_TEXT)
            image = it.getString(NOTE_IMAGE)
        }
        if (image!=null) {
            binding.image.visibility = View.VISIBLE
            binding.image.setImageURI(image?.toUri())
        }
        binding.tvNoteText.text = text
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
    companion object {
        private const val NOTE_ID = "id"
        private const val NOTE_TEXT = "name"
        private const val NOTE_IMAGE = "image"

        fun newInstance(id: Int, text: String, image: Uri?) =
            InfoFragment().apply {
                arguments = Bundle().apply {
                    putInt(NOTE_ID, id)
                    putString(NOTE_TEXT, text)
                    putString(NOTE_IMAGE, image.toString())

                }
            }
    }
}
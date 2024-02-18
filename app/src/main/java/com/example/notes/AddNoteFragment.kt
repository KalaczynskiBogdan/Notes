package com.example.notes

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.example.notes.databinding.FragmentAddNoteBinding
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

class AddNoteFragment : Fragment() {
    private val dataBase: DataBase = DataBase.getInstance()
    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!
    private lateinit var tempImageUri: Uri
    private var image: Uri? = null
    private var noteText = ""

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                binding.addImage.setImageURI(uri)
                binding.tvAddedFile.visibility = View.VISIBLE
                image = uri
            }
        }

    private val photoLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        binding.addImage.setImageURI(tempImageUri)
        val contentResolver = requireContext().contentResolver

        val contractValue = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "image_${System.currentTimeMillis()}.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        }
        val imageUriInGallery: Uri? =
            contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contractValue)

        try {
            val outputStream: OutputStream? = imageUriInGallery?.let {
                contentResolver.openOutputStream(it)
            }
            outputStream?.use { output ->
                val inputStream: InputStream? = tempImageUri.let {
                    contentResolver.openInputStream(it)
                }
                inputStream?.use { input ->
                    input.copyTo(output)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        binding.tvAddedFile.visibility = View.VISIBLE
        image = tempImageUri
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tempImageUri = initTempUri()
        binding.imageGallery.setOnClickListener {
            galleryLauncher.launch("image/*")
        }
        binding.imagePhoto.setOnClickListener {
            photoLauncher.launch(tempImageUri)
        }
        binding.button.setOnClickListener {
            saveNote()
        }
    }

    private fun saveNote() {
        val notes = dataBase.getList()
        val id = notes.size
        noteText = binding.etEnterNote.text.toString()
        val note = Note(id, noteText, image)
        dataBase.add(note)
        val fragment = NotesFragment()
        (activity as MainActivity).navigateToNextScreen(fragment)
    }

    private fun initTempUri(): Uri {
        val tempImageDir = File(
            requireContext().filesDir,
            getString(R.string.temp_images_dir)
        )
        tempImageDir.mkdir()

        val tempImage = File(
            tempImageDir,
            getString(R.string.temp_image)
        )
        return FileProvider.getUriForFile(
            requireContext(),
            getString(R.string.authorities),
            tempImage
        )
    }
}
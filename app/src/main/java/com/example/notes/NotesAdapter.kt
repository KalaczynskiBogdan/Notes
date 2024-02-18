package com.example.notes

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>(){
    private var listOfNotes: ArrayList<Note> = ArrayList()
    private lateinit var onNoteClickListener: OnNoteClickListener

    fun setOnNoteClickListener(onNoteClickListener: OnNoteClickListener) {
        this.onNoteClickListener = onNoteClickListener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setListOfNotes(notes: ArrayList<Note>) {
        this.listOfNotes = notes
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.note_item,
            parent,
            false
        )
        return NotesViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: NotesViewHolder, position: Int) {
        val note = listOfNotes[position]
        viewHolder.textViewNotes.text = note.text

        viewHolder.itemView.setOnClickListener {
            onNoteClickListener.onNoteClick(note)
        }
    }

    override fun getItemCount(): Int {
        return listOfNotes.size
    }

    class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewNotes: TextView = itemView.findViewById(R.id.tvNote)
    }

    interface OnNoteClickListener {
        fun onNoteClick(note: Note)
    }
}
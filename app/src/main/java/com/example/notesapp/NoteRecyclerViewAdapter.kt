package com.example.notesapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat

class NoteRecyclerViewAdapter(private val context:Context, private val notes:List<Note>) : RecyclerView.Adapter<NoteRecyclerViewAdapter.ViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.card_note, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notes[position]
        val date = SimpleDateFormat("dd/M/yyyy hh:mm a")
        holder.txtNote?.text = note?.note
        holder.txtTitle?.text = note?.title
        holder.txtDate?.text = note?.createdAt
    }

    override fun getItemCount(): Int  =  notes.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtTitle = itemView?.findViewById<TextView?>(R.id.tvTitle)
        val txtNote = itemView?.findViewById<TextView?>(R.id.tvNote)
        val txtDate = itemView?.findViewById<TextView?>(R.id.tvCreatedAt)
    }
}
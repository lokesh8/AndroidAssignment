package com.paycraft.assignment.notes.ui.notelist

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.paycraft.assignment.notes.R
import com.paycraft.assignment.notes.db.Note
import com.paycraft.assignment.notes.util.DiffCallback
import kotlinx.android.synthetic.main.note_items.view.*


class NoteAdapter(
    noteList: List<Note>,
    private val interaction: Interaction? = null
) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    private val notes = mutableListOf<Note>()

    init {
        notes.addAll(noteList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.note_items, parent, false)
        return ViewHolder(
            view,
            interaction
        )
    }

    override fun getItemCount() = notes.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(item = notes[position])
    }

    fun swap(notes: List<Note>) {
        val diffCallback = DiffCallback(this.notes, notes)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.notes.clear()
        this.notes.addAll(notes)
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Note) {
            itemView.txtTitle.text = item.id.toString()
            itemView.txtDescription.text = item.description
            val viewColor = if (item.tag.toString().isEmpty())"#FFFFFF" else item.tag.toString()
            itemView.cardView.setCardBackgroundColor(Color.parseColor(viewColor))
            if (item.isPin>0){
                itemView.ivPin.visibility=View.VISIBLE
            }else{
                itemView.ivPin.visibility=View.GONE
            }

            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition,item)
            }
        }

    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Note)
    }
}
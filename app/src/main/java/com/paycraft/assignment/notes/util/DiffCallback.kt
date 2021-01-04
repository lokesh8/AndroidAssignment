package com.paycraft.assignment.notes.util

import androidx.recyclerview.widget.DiffUtil
import com.paycraft.assignment.notes.db.Note

class DiffCallback(
    private val oldList: List<Note>,
    private val newList: List<Note>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
                && oldList[oldItemPosition].title == newList[newItemPosition].title
                && oldList[oldItemPosition].description == newList[newItemPosition].description
                && oldList[oldItemPosition].tag == newList[newItemPosition].tag
                && oldList[oldItemPosition].isPin == newList[newItemPosition].isPin
    }
}
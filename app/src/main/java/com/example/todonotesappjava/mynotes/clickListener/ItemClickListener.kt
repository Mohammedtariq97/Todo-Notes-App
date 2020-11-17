package com.example.todonotesappjava.mynotes.clickListener

import com.example.todonotesappjava.data.local.db.Notes


interface ItemClickListener {
    fun onCLick(notes: Notes)
    fun onUpdate(notes: Notes)
}
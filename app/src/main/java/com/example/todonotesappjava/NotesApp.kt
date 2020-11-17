package com.example.todonotesappjava

import android.app.Application
import com.androidnetworking.AndroidNetworking
import com.example.todonotesappjava.data.local.db.NotesDatabase

class NotesApp : Application(){
    override fun onCreate() {
        super.onCreate()
        AndroidNetworking.initialize(applicationContext);
    }

    fun getNotesDb(): NotesDatabase {
        return NotesDatabase.getInstance(this)
    }
}
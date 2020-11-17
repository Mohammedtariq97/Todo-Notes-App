package com.example.todonotesappjava.mynotes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Constraints
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.todonotesappjava.NotesApp
import com.example.todonotesappjava.utils.AppConstant
import com.example.todonotesappjava.R
import com.example.todonotesappjava.mynotes.adapter.NotesAdapter
import com.example.todonotesappjava.mynotes.clickListener.ItemClickListener
import com.example.todonotesappjava.data.local.db.Notes
import com.example.todonotesappjava.data.local.pref.StoreSession

import com.example.todonotesappjava.data.local.pref.prefConstant
import com.example.todonotesappjava.utils.workManager.myWorker
import com.example.todonotesappjava.addnotes.AddNotesActivity
import com.example.todonotesappjava.blog.BlogActivity
import com.example.todonotesappjava.detail.DetailActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.concurrent.TimeUnit

public class MyNotesActivity :AppCompatActivity() {
    companion object {
        private const val TAG = "MyNotesActivity"
        const val ADD_NOTES_CODE = 100
    }
    var fullName:String=""
    lateinit var fabAddNotes :FloatingActionButton
    lateinit var recyclerViewNotes:RecyclerView
    var notesList= ArrayList<Notes>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_notes)
        bindViews()
        setUpSharedPreferences()
        setFullName()
        getDataFromDataBase()
        setupRecyclerView()
        setUpWorkManager()
        fabAddNotes.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val intent = Intent(this@MyNotesActivity, AddNotesActivity::class.java)
                startActivityForResult(intent, ADD_NOTES_CODE)
            }
        })
    }

    private fun setUpWorkManager() {
        val constraint = Constraints.Builder()
                .build()
        val request = PeriodicWorkRequest
                .Builder(myWorker::class.java,1,TimeUnit.MINUTES)
                .setConstraints(constraint)
                .build()
        WorkManager.getInstance().enqueue(request)
        //WorkManager.getInstance().beginWith(request).then(myWorker).enqueue()
    }

    private fun setFullName() {
        if (intent.hasExtra(AppConstant.FULL_NAME)) {
            fullName = intent.getStringExtra(AppConstant.FULL_NAME)!!
        }
        if (fullName.isEmpty()) {
            fullName = StoreSession.readString(prefConstant.FULL_NAME)!!
            //fullName = sharedPreferences.getString(prefConstant.FULL_NAME, "") ?: ""
        }
        supportActionBar?.title=fullName
        //fullName = sharedPreferences.getString(prefConstant.FULL_NAME,"").toString()
        //supportActionBar?.title = fullName
    }

    private fun getDataFromDataBase() {
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDb().notesDao()
        Log.d("MyNotesActivity",notesDao.getAll().size.toString())
        notesList.addAll(notesDao.getAll())
    }

    private fun addNotesToDb(notes: Notes) {
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDb().notesDao()
        notesDao.insert(notes)
    }

    private fun setupRecyclerView() {
        val itemClickListener = object : ItemClickListener {
            override fun onUpdate(notes: Notes) {
                    Log.d(TAG,notes.isTaskCompleted.toString())
                val notesApp = applicationContext as NotesApp
                val notesDao = notesApp.getNotesDb().notesDao()
                notesDao.updateNotes(notes)
            }
            override fun onCLick(notes: Notes) {
                val intent = Intent(this@MyNotesActivity, DetailActivity::class.java)
                intent.putExtra(AppConstant.TITLE,notes.title)
                intent.putExtra(AppConstant.DESCRIPTION,notes.description)
                startActivity(intent)
            }
        }
        val notesAdapter = NotesAdapter(notesList, itemClickListener)
        val linearLayoutManager= LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerViewNotes.layoutManager=linearLayoutManager
        recyclerViewNotes.adapter = notesAdapter
    }

    private fun setUpSharedPreferences() {
        StoreSession.init(this)
    }

    private fun bindViews() {
        fabAddNotes = findViewById(R.id.fabAddNotes)
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) { //should be created when using startActivityForResult
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode== ADD_NOTES_CODE && resultCode == Activity.RESULT_OK){
            val title = data?.getStringExtra(AppConstant.TITLE)
            val description = data?.getStringExtra(AppConstant.DESCRIPTION)
            val imagePath = data?.getStringExtra(AppConstant.IMAGE_PATH)
            Log.d(TAG, title.toString())
            Log.d(TAG,description.toString())

            val notesApp = applicationContext as NotesApp
            val notesDao = notesApp.getNotesDb().notesDao()
            val note = Notes(title = title!!, description = description!!, imagePath = imagePath!!)

            notesList.add(note)
            notesDao.insert(note)
            recyclerViewNotes.adapter?.notifyItemChanged(notesList.size-1)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.blog){
            Log.d(TAG,"Click Successful")
            val intent = Intent(this, BlogActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}
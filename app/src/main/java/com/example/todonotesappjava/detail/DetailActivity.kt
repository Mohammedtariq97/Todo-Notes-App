package com.example.todonotesappjava.detail

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.todonotesappjava.utils.AppConstant
import com.example.todonotesappjava.R

class DetailActivity :AppCompatActivity() {
    val TAG = "DetailActivity"
    lateinit var textViewTitle:TextView
    lateinit var textViewDescription:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        bindViews();
        getIntentData();
    }

    private fun bindViews() {
        textViewTitle=findViewById(R.id.textViewTitle)
        textViewDescription=findViewById(R.id.textViewDescription)
    }

    private fun getIntentData() {
        val intent = intent
        val title = intent.getStringExtra(AppConstant.TITLE)
        val description = intent.getStringExtra(AppConstant.DESCRIPTION)
        textViewTitle.setText(title)
        textViewDescription.setText(description)
    }
}
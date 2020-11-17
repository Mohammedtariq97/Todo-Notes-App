package com.example.todonotesappjava.blog

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.example.todonotesappjava.R
import com.example.todonotesappjava.blog.adapter.BlogAdapter
import com.example.todonotesappjava.data.remote.JsonResponse


class BlogActivity : AppCompatActivity() {
    lateinit var recyclerViewBLogs :RecyclerView
    val TAG = "BlogActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blog)
        bindViews()
        getBlogs()
    }

    private fun getBlogs() {
        AndroidNetworking.get("http://www.mocky.io/v2/5926ce9d11000096006ccb30")
                .setPriority(Priority.HIGH)
                .build()
                .getAsObject(JsonResponse::class.java,object : ParsedRequestListener<JsonResponse> {
                    override fun onResponse(response: JsonResponse?) {
                        setUpRecyclerView(response)
                    }

                    override fun onError(anError: ANError?) {
                        Log.d(TAG, anError?.localizedMessage.toString())
                    }
                })
    }

    private fun bindViews() {
        recyclerViewBLogs = findViewById(R.id.recyclerViewBlogs)
    }

    private fun setUpRecyclerView(response: JsonResponse?) {
        val blogAdapter = BlogAdapter(response!!.data)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerViewBLogs.layoutManager = linearLayoutManager
        recyclerViewBLogs.adapter = blogAdapter

    }
}
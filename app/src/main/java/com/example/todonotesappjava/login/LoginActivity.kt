package com.example.todonotesappjava.login

import android.content.Intent

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.example.todonotesappjava.utils.AppConstant
import com.example.todonotesappjava.R
import com.example.todonotesappjava.data.local.pref.StoreSession
import com.example.todonotesappjava.data.local.pref.prefConstant
import com.example.todonotesappjava.mynotes.MyNotesActivity

class LoginActivity : AppCompatActivity() {
    lateinit var  editTextFullName:EditText
    lateinit var  editTextUserName:EditText
    lateinit var buttonLogin:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        bindViews()
        setUpSharedPreferences()

    }

    private fun setUpSharedPreferences() {
        StoreSession.init(this)
    }

    private fun bindViews() {
        editTextFullName = findViewById(R.id.editTextFullName)
        editTextUserName = findViewById(R.id.editTextUserName)
        buttonLogin=findViewById(R.id.buttonLogin)
        val clickAction = object: View.OnClickListener {
            override fun onClick(v: View?) {
                val fullName= editTextFullName.text.toString()
                val userName = editTextUserName.text.toString()
                if(fullName.isNotEmpty()&& userName.isNotEmpty()){
                    val intent = Intent(this@LoginActivity, MyNotesActivity::class.java)
                    intent.putExtra(AppConstant.FULL_NAME,fullName)
                    startActivity(intent)
                    saveLoginStatus()
                    saveFullName(fullName)
                }else{
                    Toast.makeText(this@LoginActivity,"UserName and Password cant be empty",Toast.LENGTH_SHORT).show()
                }
            }
        }
        buttonLogin.setOnClickListener(clickAction)
        }

    private fun saveFullName(fullName: String) {
        StoreSession.write(prefConstant.FULL_NAME,fullName)
    }

    private fun saveLoginStatus() {
        StoreSession.write(prefConstant.IS_LOGGED_IN,true)
    }
}
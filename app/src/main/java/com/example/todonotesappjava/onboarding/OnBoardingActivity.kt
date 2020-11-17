package com.example.todonotesappjava.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.todonotesappjava.R
import com.example.todonotesappjava.data.local.pref.StoreSession
import com.example.todonotesappjava.data.local.pref.prefConstant
import com.example.todonotesappjava.login.LoginActivity

class OnBoardingActivity : AppCompatActivity(),OnBoardingOneFragment.OnNextClick,OnBoardingTwoFragment.OnOptionClick {
    companion object {
        private const val FIRST_ITEM = 0
        private const val LAST_ITEM = 1
    }
    lateinit var viewPager:ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
        bindview()
        setUpSharedPreferences()
    }

    private fun setUpSharedPreferences() {
        StoreSession.init(this)
    }

    private fun bindview() {
        viewPager = findViewById(R.id.viewPager)
        val pageAdapter = FragmentAdapter(this)
        viewPager.adapter=pageAdapter
    }

    override fun onClick() {
        viewPager.currentItem= LAST_ITEM
    }

    override fun onOptionBack() {
        viewPager.currentItem= FIRST_ITEM
    }

    override fun onOptionDone() {
        StoreSession.write(prefConstant.ON_BOARDED_SUCCESSFULLY,true)
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}
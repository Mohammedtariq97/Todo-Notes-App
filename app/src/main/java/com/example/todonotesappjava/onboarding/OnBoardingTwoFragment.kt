package com.example.todonotesappjava.onboarding

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.todonotesappjava.R

class OnBoardingTwoFragment : Fragment() {
    lateinit var buttonBack : Button
    lateinit var buttonDone : Button
    lateinit var onOptionClick : OnOptionClick

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onOptionClick = context as OnOptionClick
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_on_boarding_two, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
    }

    private fun bindViews(view: View) {
        buttonBack=view.findViewById(R.id.buttonBack)
        buttonDone=view.findViewById(R.id.buttonDone)
        clickListener()
    }

    private fun clickListener() {
        buttonBack.setOnClickListener(object:View.OnClickListener{
            override fun onClick(v: View?) {
                onOptionClick.onOptionBack()
            }

        })
        buttonDone.setOnClickListener(object:View.OnClickListener{
            override fun onClick(v: View?) {
                onOptionClick.onOptionDone()
            }

        })
    }

    interface OnOptionClick{
        fun onOptionBack()
        fun onOptionDone()
    }
}
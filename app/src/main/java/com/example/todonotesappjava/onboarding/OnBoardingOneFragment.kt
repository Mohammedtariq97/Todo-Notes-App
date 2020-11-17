package com.example.todonotesappjava.onboarding

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.todonotesappjava.R

class OnBoardingOneFragment : Fragment() {

    lateinit var buttonNext:Button
    lateinit var onNextClick: OnNextClick
    val TAG = "OnBoardingOneFragment"

    override fun onAttach(context: Context) {
        Log.d(TAG,"onAttach called")
        super.onAttach(context)
        onNextClick = context as OnNextClick
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_on_boarding_one, container, false)
        Log.d(TAG,"onCreateView called")

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView(view)
    }

    private fun bindView(view: View) {
        buttonNext = view.findViewById<Button>(R.id.buttonNext)
        clickListeners()
    }

    private fun clickListeners() {
        buttonNext.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                onNextClick.onClick()
            }

        })
    }

    interface OnNextClick{
        fun onClick()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"onCreate called")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(TAG,"onActivityCreated called")
    }
    override fun onStart() {
        super.onStart()
        Log.d(TAG,"onStart Called")
    }
    override fun onResume() {
        super.onResume()
        Log.d(TAG,"onResume called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "OnPause called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG,"onStop called")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG,"onDestroyView called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"onDestroy called")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG,"onDetach called")
    }
}
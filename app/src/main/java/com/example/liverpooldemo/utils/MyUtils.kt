package com.example.liverpooldemo.utils

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.app.Activity
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.ContextWrapper
import android.content.SharedPreferences
import android.graphics.Bitmap

import android.util.Base64
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager


import java.io.ByteArrayOutputStream
import java.io.File
import java.lang.Exception
import java.util.*

class MyUtils {



    companion object {


        fun getMySharedPreferences(context: Context): SharedPreferences? {
            val sharedPref = context?.getSharedPreferences("asda", Context.MODE_PRIVATE)
            return sharedPref
        }

        val APP_NAME = "LIVERPOOL"


        fun animateView(view: View, delay: Long = 0) {
            val propertyY = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 100f, 0.0f)
            val propertyAlfa = PropertyValuesHolder.ofFloat("alpha", 0f, 1f)
            val animator = ObjectAnimator.ofPropertyValuesHolder(view, propertyY, propertyAlfa)
            animator.duration = 600
            animator.startDelay = delay
            animator.start()

        }

       fun hideSoftKeyboard(activity: Activity) {
            try{
                val inputMethodManager = activity.getSystemService(
                    Activity.INPUT_METHOD_SERVICE
                ) as InputMethodManager
                inputMethodManager!!.hideSoftInputFromWindow(
                    activity.currentFocus!!.windowToken, 0
                )
            }catch (e : Exception){

            }

        }

    }

}
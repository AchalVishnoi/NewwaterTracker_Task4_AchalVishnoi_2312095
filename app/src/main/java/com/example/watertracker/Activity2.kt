package com.example.watertracker

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Activity2 : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
      }


        val b1:ImageView=findViewById(R.id.next)
        var page=1
        b1.setOnClickListener{
            if(page==1){
                findViewById<ImageView>(R.id.imageView10).setImageResource(R.drawable.waterday1)
                findViewById<ImageView>(R.id.imageView13).setImageResource(R.drawable.dotider)
                findViewById<TextView>(R.id.textView9).setText("Smart Reminders Tailored to You")
                findViewById<TextView>(R.id.textView).setText("Quick and easy to set your hydration goal & then track your daily water intake progress.")
              page++;
            }
            else if(page==2){
                findViewById<ImageView>(R.id.imageView10).setImageResource(R.drawable.bottleofwaterrafiki1)
                findViewById<ImageView>(R.id.imageView13).setImageResource(R.drawable.dotsider3)
                findViewById<ImageView>(R.id.next).setImageResource(R.drawable.get_start)
                findViewById<TextView>(R.id.textView9).setText("Easy to Use – Drink, Tap, Repeat")
                findViewById<TextView>(R.id.textView).setText("Staying hydrated every day is easy with Drops Water Tracker.")
                 page++;
            }
        }



    }

    }

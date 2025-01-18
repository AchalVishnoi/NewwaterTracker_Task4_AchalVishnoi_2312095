package com.example.watertracker

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.watertracker.databinding.Activity3Binding
import com.example.watertracker.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class Activity3 : AppCompatActivity() {





    private lateinit var binding : Activity3Binding


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_3)
        binding=Activity3Binding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(home())











        binding.bottomNavigationView.setOnItemSelectedListener {




            when(it.itemId){
                R.id.home-> {
                    replaceFragment(home())
                    findViewById<BottomNavigationView>(R.id.bottomNavigationView).itemTextColor
                }

                R.id.analysis->{
                    replaceFragment(analysis())}

                R.id.profile->{
                    replaceFragment(profileFraagment())
                }
                else ->{}

            }
            true
        }





    }

    private fun replaceFragment(fragment : Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()


    }






}



package com.example.watertracker
import android.annotation.SuppressLint
import android.os.Handler
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Constraints
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat



class MainActivity : AppCompatActivity() {


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        title = "KotlinApp"

        var img:ConstraintLayout=findViewById(R.id.main2)
        img.setOnClickListener{
            val i = Intent(this, Activity2::class.java)
            startActivity(i)

        }

        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(this, Activity2::class.java)
            startActivity(intent)


          }, 3000)


    }





}







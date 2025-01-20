package com.example.watertracker

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import backend.addDetailRequest
import backend.response
import mobile.kotlinexamples.fragments.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class AddDetailsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_details)
        /* ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/


        var sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE)

        // Retrieve the token from SharedPreferences
        val userId = sharedPreferences.getString("USER_ID", null)

        val email = intent.getStringExtra("EXTRA_EMAIL")
        val password = intent.getStringExtra("EXTRA_PASSWORD")


        // Now, you can use the token in your request headers
        if (userId != null) {

            var radioGroup = findViewById<RadioGroup>(R.id.radioGroup)




            var seekBar1 = findViewById<SeekBar>(R.id.ageSeekBar)
            var seekBar2 = findViewById<SeekBar>(R.id.weightSeekBar)
            var seekBar3 = findViewById<SeekBar>(R.id.heightSeekBar)

            var seekBarValueText1 = findViewById<TextView>(R.id.ageValueText)
            var seekBarValueText2 = findViewById<TextView>(R.id.weightValueText)
            var seekBarValueText3 = findViewById<TextView>(R.id.heightValueText)
            seekBar1.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {

                    seekBarValueText1.text = "Value: $progress"
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}

                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })
            seekBar2.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {

                    seekBarValueText2.text = "Value: $progress"
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}

                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })
            seekBar3.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {

                    seekBarValueText3.text = "Value: $progress"
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}

                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })

            findViewById<Button>(R.id.nextButton).setOnClickListener {

                findViewById<Button>(R.id.nextButton).visibility= View.GONE
                findViewById<ProgressBar>(R.id.updateProgress).visibility=View.VISIBLE


                val selectedId = radioGroup.checkedRadioButtonId

                val selectedRadioButton = findViewById<RadioButton>(selectedId)

                val gender = selectedRadioButton.text.toString()

                val requestBody = addDetailRequest(
                    userId=userId.toInt(),
                    age = seekBar1.progress,
                    gender = gender.toString(),
                    sleepTime = "10:20 AM",
                    wakeUpTime = "06:10 PM",
                    weight = seekBar2.progress.toDouble(),
                    height = seekBar3.progress.toDouble()
                )
                val call = RetrofitInstance.apiService.addDetails(requestBody)
                call.enqueue(object  : Callback<response> {
                    override fun onResponse(
                        call: Call<response>,
                        response: Response<response>
                    ) {
                        Log.e("",response.message() )

                        if (response.isSuccessful) {

                            Log.d("jjj", userId)


                            val intent = Intent(this@AddDetailsActivity, SignIn::class.java)

                            startActivity(intent)
                            finish()
                        }
                        else {

                        }
                    }

                    override fun onFailure(call: Call<response>, t: Throwable) {

                    }
                })
            }

        }
        else {
            Toast.makeText(this, "Token not found", Toast.LENGTH_SHORT).show()
        }
    }


    @SuppressLint("NewApi")
    fun convertToLocalTime(time: String): LocalTime {
        val formatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.getDefault())
        return LocalTime.parse(time, formatter)
    }


}
package com.example.watertracker

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import backend.create_user_response
import backend.logWaterIntakeRequest
import backend.logWaterIntakeResponse
import backend.loginRequest
import com.example.watertracker.SignIn
import mobile.kotlinexamples.fragments.RetrofitInstance
import mobile.kotlinexamples.fragments.com.example.watertracker.LoadingDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log
import android.os.Handler
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar

class logWaterIntake : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_log_water_intake)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)

        val token = sharedPreferences.getString("ACCESS_TOKEN", null)

        findViewById<ImageView>(R.id.a).setOnClickListener {



         /*   val loading = LoadingDialog(this)
            loading.startLoading()
            val handler = Handler()
            handler.postDelayed(object :Runnable{
                override fun run() {
                    loading.isDismiss()
                }

            },5000)*/

            findViewById<LinearLayout>(R.id.waterLogItem).visibility= View.GONE
            findViewById<ProgressBar>(R.id.logWaterLoading).visibility= View.VISIBLE

            val waterIntake = logWaterIntakeRequest(120)
            RetrofitInstance.apiService.logWaterIntake("Bearer $token", waterIntake)
                .enqueue(object : Callback<logWaterIntakeResponse> {
                    override fun onResponse(
                        call: Call<logWaterIntakeResponse>,
                        response: Response<logWaterIntakeResponse>
                    ) {
                        if (response.isSuccessful) {

                            Toast.makeText(
                                this@logWaterIntake,
                                "Water intake logged successfully!",
                                Toast.LENGTH_SHORT
                            ).show()

                            var intent=Intent(this@logWaterIntake, Activity3::class.java)
                            startActivity(intent);
                        } else {

                            Toast.makeText(
                                this@logWaterIntake,
                                "Error: ${response.code()} - ${response.message()}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<logWaterIntakeResponse>, t: Throwable) {

                        Toast.makeText(
                            this@logWaterIntake,
                            "Failed: ${t.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }



        findViewById<ImageView>(R.id.b).setOnClickListener {


/*   val loading = LoadingDialog(this)
            loading.startLoading()
            val handler = Handler()
            handler.postDelayed(object :Runnable{
                override fun run() {
                    loading.isDismiss()
                }

            },5000)*/

            findViewById<LinearLayout>(R.id.waterLogItem).visibility= View.GONE
            findViewById<ProgressBar>(R.id.logWaterLoading).visibility= View.VISIBLE


            val waterIntake = logWaterIntakeRequest(130)
            RetrofitInstance.apiService.logWaterIntake("Bearer $token", waterIntake)
                .enqueue(object : Callback<logWaterIntakeResponse> {
                    override fun onResponse(
                        call: Call<logWaterIntakeResponse>,
                        response: Response<logWaterIntakeResponse>
                    ) {
                        if (response.isSuccessful) {

                            Toast.makeText(
                                this@logWaterIntake,
                                "Water intake logged successfully!",
                                Toast.LENGTH_SHORT
                            ).show()

                            var intent=Intent(this@logWaterIntake, Activity3::class.java)
                            startActivity(intent);
                        } else {

                            Toast.makeText(
                                this@logWaterIntake,
                                "Error: ${response.code()} - ${response.message()}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<logWaterIntakeResponse>, t: Throwable) {

                        Toast.makeText(
                            this@logWaterIntake,
                            "Failed: ${t.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }


        findViewById<ImageView>(R.id.c).setOnClickListener {


/*   val loading = LoadingDialog(this)
            loading.startLoading()
            val handler = Handler()
            handler.postDelayed(object :Runnable{
                override fun run() {
                    loading.isDismiss()
                }

            },5000)*/

            findViewById<LinearLayout>(R.id.waterLogItem).visibility= View.GONE
            findViewById<ProgressBar>(R.id.logWaterLoading).visibility= View.VISIBLE


            val waterIntake = logWaterIntakeRequest(140)
            RetrofitInstance.apiService.logWaterIntake("Bearer $token", waterIntake)
                .enqueue(object : Callback<logWaterIntakeResponse> {
                    override fun onResponse(
                        call: Call<logWaterIntakeResponse>,
                        response: Response<logWaterIntakeResponse>
                    ) {
                        if (response.isSuccessful) {

                            Toast.makeText(
                                this@logWaterIntake,
                                "Water intake logged successfully!",
                                Toast.LENGTH_SHORT
                            ).show()

                            var intent=Intent(this@logWaterIntake, Activity3::class.java)
                            startActivity(intent);
                        } else {

                            Toast.makeText(
                                this@logWaterIntake,
                                "Error: ${response.code()} - ${response.message()}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<logWaterIntakeResponse>, t: Throwable) {

                        Toast.makeText(
                            this@logWaterIntake,
                            "Failed: ${t.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }



        findViewById<ImageView>(R.id.d).setOnClickListener {



            /*   val loading = LoadingDialog(this)
               loading.startLoading()
               val handler = Handler()
               handler.postDelayed(object :Runnable{
                   override fun run() {
                       loading.isDismiss()
                   }

               },5000)*/

            findViewById<LinearLayout>(R.id.waterLogItem).visibility= View.GONE
            findViewById<ProgressBar>(R.id.logWaterLoading).visibility= View.VISIBLE


            val waterIntake = logWaterIntakeRequest(150)
            RetrofitInstance.apiService.logWaterIntake("Bearer $token", waterIntake)
                .enqueue(object : Callback<logWaterIntakeResponse> {
                    override fun onResponse(
                        call: Call<logWaterIntakeResponse>,
                        response: Response<logWaterIntakeResponse>
                    ) {




                        if (response.isSuccessful) {

                            Toast.makeText(
                                this@logWaterIntake,
                                "Water intake logged successfully!",
                                Toast.LENGTH_SHORT
                            ).show()

                            var intent=Intent(this@logWaterIntake, Activity3::class.java)
                            startActivity(intent);
                        } else {

                            Toast.makeText(
                                this@logWaterIntake,
                                "Error: ${response.code()} - ${response.message()}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<logWaterIntakeResponse>, t: Throwable) {

                        Toast.makeText(
                            this@logWaterIntake,
                            "Failed: ${t.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }

        findViewById<ImageView>(R.id.btnClose).setOnClickListener {
            var intent = Intent(this@logWaterIntake, Activity3::class.java)
            startActivity(intent);
        }

        Log.d("", "onCreate: $token")



    }
}
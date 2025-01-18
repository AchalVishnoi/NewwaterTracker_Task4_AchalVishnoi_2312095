package com.example.watertracker

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import backend.create_user
import backend.create_user_response
import backend.loginRequest

import com.example.watertracker.SignUpActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import mobile.kotlinexamples.fragments.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class SignIn : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_in)

        var sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE)


       val emailEditText = findViewById<EditText>(R.id.signInEmailEditText)
        val passwordEditText = findViewById<EditText>(R.id.signInPasswordEditText)






        val signupTextView = findViewById<TextView>(R.id.signupText)

        signupTextView.setOnClickListener {

            signupTextView.setBackgroundColor(Color.parseColor("#cef0fe"))

            val intent = Intent(this@SignIn, SignUpActivity::class.java)
            startActivity(intent)

            signupTextView.postDelayed({

                signupTextView.setBackgroundColor(Color.TRANSPARENT)
            }, 200)
        }
        val emailText=emailEditText.text.toString().trim()
        val passwordText=passwordEditText.text.toString().trim()
        var isEmailValid: Boolean=false;
        var isPasswordValid=false;
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"




        fun updateButtonState() {

            val signInButton = findViewById<Button>(R.id.signinButton)
            if (isPasswordValid && isEmailValid) {

                signInButton.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#62CDFA"))

            signInButton.setOnClickListener {





                    val emailText = emailEditText.text.toString().trim()
                    val passwordText = passwordEditText.text.toString().trim()
                    if (emailText.isEmpty() || passwordText.isEmpty()) {
                        Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }

                    val login = loginRequest(emailText, passwordText)
                    RetrofitInstance.apiService.signInUser(login)
                        .enqueue(object : Callback<create_user_response> {
                            override fun onResponse(
                                call: Call<create_user_response>,
                                response: Response<create_user_response>
                            ) {
                                if (response.isSuccessful) {
                                    val token = response.body()?.token
                                    if (token != null) {

                                        sharedPreferences.edit()
                                            .putString("ACCESS_TOKEN", token.toString()).apply()

                                        Toast.makeText(
                                            this@SignIn,
                                            "Sign-in successful!",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        val intent = Intent(this@SignIn, Activity3::class.java)
                                        startActivity(intent)
                                        finish()
                                    } else {
                                        Toast.makeText(
                                            this@SignIn,
                                            "Failed to retrieve token",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                } else {
                                    val errorBody = response.errorBody()?.string()
                                    Toast.makeText(
                                        this@SignIn,
                                        "Error: $errorBody",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                            }

                            override fun onFailure(call: Call<create_user_response>, t: Throwable) {
                                Toast.makeText(
                                    this@SignIn,
                                    "Failed: ${t.message}",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        })
                }


            }

            else{
                signInButton.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#afe3ef"))
                signInButton.setOnClickListener(null)

            }
        }

        emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                isEmailValid = if (emailEditText.text.toString().matches(emailPattern.toRegex())) {
                    emailEditText.error = null
                    true
                } else {
                    emailEditText.error = "Invalid email address"
                    false
                }
                updateButtonState()
            }
        })


        passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                isPasswordValid= if (passwordEditText.text.toString().length >= 6) {
                    passwordEditText.error = null
                    true
                } else {
                    passwordEditText.error = "Password must be at least 6 characters"
                    false
                }
                updateButtonState()
            }
        })






    }


    }





         /*   CoroutineScope(Dispatchers.Main).launch {
                try {
                    // Call the login API
                    val response = RetrofitInstance.apiService.signInUser(login)

                    if (response.isSuccessful) {

                        val loginResponse = response.body()
                        val accessToken = loginResponse?.token ?: ""

                        if (accessToken.isNotEmpty()) {

                            val sharedPreferences =
                                getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                            sharedPreferences.edit().apply {
                                putString("access_token", accessToken)
                                apply()
                            }

                            // Display success and navigate to Home
                            Toast.makeText(this@SignIn, "Login Successful!", Toast.LENGTH_SHORT)
                                .show()
                            startActivity(Intent(this@SignIn, Activity3::class.java))
                            finish()
                        } else {
                            Toast.makeText(
                                this@SignIn,
                                "Login failed. Empty token received.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        // Parse error response if available
                        val errorBody = response.errorBody()?.string()
                        Log.d("LoginError", "Error Body: $errorBody")

                        if (errorBody?.contains("Invalid username or password") == true) {


                            Toast.makeText(
                                this@SignIn,
                                "Invalid username or password.",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else if (errorBody?.contains("No active account found with the given credentials") == true) {
                            Toast.makeText(
                                this@SignIn,
                                "No active account found with the given credentials",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                this@SignIn,
                                "Login failed. Try again.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
                } catch (e: Exception) {
                    // Handle API call failure
                    Toast.makeText(this@SignIn, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    Log.e("LoginException", e.message ?: "Unknown error")
                }
            }*/


package com.example.watertracker

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import backend.createUserResponse
import backend.create_user
import backend.create_user_response
import mobile.kotlinexamples.fragments.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)

        sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE)

        val signupTextView = findViewById<TextView>(R.id.signinText)

        signupTextView.setOnClickListener {

            signupTextView.setBackgroundColor(Color.parseColor("#cef0fe"))

            val intent = Intent(this@SignUpActivity, SignIn::class.java)
            startActivity(intent)

            signupTextView.postDelayed({

                signupTextView.setBackgroundColor(Color.TRANSPARENT)
            }, 200)
        }

        //retrofit and set on slick listener on button



        val firstName =
            findViewById<EditText>(R.id.firstNameEditText);
        val lastName =
            findViewById<EditText>(R.id.lastNameEditText);

        val email = findViewById<EditText>(R.id.emailEditText);
        val password = findViewById<EditText>(R.id.passwordEditText);
        var isEmailValid: Boolean=false;
        var isPasswordValid=false;
        var isFirstName=false;
        var isLastName=false;

        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"


        fun updateButtonState() {

            val signUpButton = findViewById<Button>(R.id.signUpButton)
            if (isPasswordValid && isEmailValid&&isFirstName&&isLastName) {


                signUpButton.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#62CDFA"))

                signUpButton.setOnClickListener {
                    val firstName =
                        findViewById<EditText>(R.id.firstNameEditText).text.toString().trim()
                    val lastName =
                        findViewById<EditText>(R.id.lastNameEditText).text.toString().trim()
                    val email = findViewById<EditText>(R.id.emailEditText).text.toString().trim()
                    val password =
                        findViewById<EditText>(R.id.passwordEditText).text.toString().trim()

                    if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                        Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }

                    val user = create_user(firstName, lastName, email, password)

                    RetrofitInstance.apiService.createUser(user)
                        .enqueue(object : Callback<createUserResponse> {
                            override fun onResponse(
                                call: Call<createUserResponse>,
                                response: Response<createUserResponse>
                            ) {
                                if (response.isSuccessful) {
                                    val id = response.body()?.userId
                                    if (id != null) {

                                        sharedPreferences.edit().putString("USER_ID", id.toString())
                                            .apply()

                                        Toast.makeText(
                                            this@SignUpActivity,
                                            "Sign-up successful!",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        val intent = Intent(
                                            this@SignUpActivity,
                                            AddDetailsActivity::class.java
                                        )

                                        startActivity(intent)
                                        finish()
                                    } else {
                                        Toast.makeText(
                                            this@SignUpActivity,
                                            "Failed to retrieve token",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                } else {
                                    val errorBody = response.errorBody()?.string()
                                    Toast.makeText(
                                        this@SignUpActivity,
                                        "Error: $errorBody",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                            }

                            override fun onFailure(call: Call<createUserResponse>, t: Throwable) {
                                Toast.makeText(
                                    this@SignUpActivity,
                                    "Failed: ${t.message}",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        })
                }
            }

            else{


                signUpButton.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#afe3ef"))
                signUpButton.setOnClickListener(null)
             }

        }


        email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                isEmailValid = if (email.text.toString().matches(emailPattern.toRegex())) {
                    email.error = null
                    true
                } else {
                    email.error = "Invalid email address"
                    false
                }
                updateButtonState()
            }
        })


        password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
           isPasswordValid= if (password.text.toString().length >= 6) {
                    password.error = null
                    true
                } else {
                    password.error = "Password must be at least 6 characters"
                    false
                }
                updateButtonState()
            }
        })


        firstName.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                 }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {

            }

            override fun afterTextChanged(s: Editable?) {
           isFirstName= if (!firstName.text.toString().isEmpty()) {
                    firstName.error = null
                    true
                } else {
                    firstName.error = "field required"
                    false
                }

                updateButtonState()

            }

        })
 lastName.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                 }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {

            }

            override fun afterTextChanged(s: Editable?) {
         isLastName=   if (!lastName.text.toString().isEmpty()) {
                    lastName.error = null
                    true
                } else {
                    lastName.error = "field required"
                    false
                }

                updateButtonState()

            }

        })


    }
}
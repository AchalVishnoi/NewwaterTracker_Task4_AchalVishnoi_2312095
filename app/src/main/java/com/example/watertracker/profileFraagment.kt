package com.example.watertracker

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import backend.createUserResponse
import backend.getUserProfile
import com.example.watertracker.SignUpActivity
import mobile.kotlinexamples.fragments.RetrofitInstance
import mobile.kotlinexamples.fragments.com.example.watertracker.ProfileFragmentViewModel

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class profileFraagment : Fragment() {


    //private val sharedViewModel: SharedProfileViewModel by activityViewModels()
    private val view: ProfileFragmentViewModel by viewModels()





    companion object {
        fun newInstance() = profileFraagment()
    }

    private val viewModel: ProfileFraagmentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    @SuppressLint("MissingInflatedId", "UseRequireInsteadOfGet", "SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var rootView= inflater.inflate(R.layout.fragment_profile_fraagment, container, false)

        var edit=rootView.findViewById<ImageButton>(R.id.edit)
        edit.setOnClickListener{
            val fragmentTransaction = fragmentManager!!.beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout,editProfile())
            fragmentTransaction.commit()
        }


       var productnameText = arguments?.getString("first")
        var quantityText = arguments?.getString("last")
        var PriceText = arguments?.getString("mail")
        var Age= arguments?.getString("age")

     var first=rootView.findViewById<TextView>(R.id.first)
        var last=rootView.findViewById<TextView>(R.id.last)
      var mail=rootView.findViewById<TextView>(R.id.mail)
      var age=rootView.findViewById<TextView>(R.id.age)
        val profileImageView:ImageView = rootView.findViewById(R.id.profileImage)

        val sharedPreferences = requireContext().getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)


        val token = sharedPreferences.getString("ACCESS_TOKEN", null)
//        if (token != null) {
//            fetchUserProfile(token)
//
//            // Observe shared ViewModel for user profile data updates
//
//                // Set text fields
//                first.setText(getUserProfile.firstName)
//                last.setText(getUserProfile.lastName)
//                mail.setText(getUserProfile.email)
//                age.setText(getUserProfile.age.toString())
//
//                // Gender-based RadioButton selection and profile image update
//                when (getUserProfile.gender?.lowercase()) {
//                    "male" -> {
//                        rootView.findViewById<RadioButton>(R.id.maleRadio).isChecked = true
//                        profileImageView.setImageResource(R.drawable.male_user)
//                    }
//                    "female" -> {
//                        rootView.findViewById<RadioButton>(R.id.femaleRadio).isChecked = true
//                        profileImageView.setImageResource(R.drawable.profile_pic)
//                    }
//                    else -> {
//                        rootView.findViewById<RadioButton>(R.id.otherRadio).isChecked = true
//                        profileImageView.setImageResource(R.drawable.others_user)
//                    }
//
//            }
//        }
//        else {
//
//            Toast.makeText(requireContext(), "Token not found. Please log in again.", Toast.LENGTH_SHORT).show()
//        }


        if (token != null) {
            view.fetchUserProfile(token)
        } else {
            Toast.makeText(requireContext(), "Token not found. Please log in again.", Toast.LENGTH_SHORT).show()
        }

        view.userProfile.observe(viewLifecycleOwner) { userProfile ->
            if (userProfile != null) {
                first.text = userProfile.firstName
                last.text = userProfile.lastName
                mail.text = userProfile.email
                age.text = userProfile.age.toString()

                // Set gender-based profile picture
                when (userProfile.gender?.lowercase()) {
                    "male" -> {
                        rootView.findViewById<RadioButton>(R.id.maleRadio).isChecked = true
                        profileImageView.setImageResource(R.drawable.male_user)
                    }
                    "female" -> {
                        rootView.findViewById<RadioButton>(R.id.femaleRadio).isChecked = true
                        profileImageView.setImageResource(R.drawable.profile_pic)
                    }
                    else -> {
                        rootView.findViewById<RadioButton>(R.id.otherRadio).isChecked = true
                        profileImageView.setImageResource(R.drawable.others_user)
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Failed to fetch profile data.", Toast.LENGTH_SHORT).show()
            }
        }




        return rootView
    }



}







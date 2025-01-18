package com.example.watertracker

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import mobile.kotlinexamples.fragments.com.example.watertracker.ProfileFragmentViewModel
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.getValue

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [home.newInstance] factory method to
 * create an instance of this fragment.
 */
class home : Fragment() {
    // TODO: Rename and change types of parameters

    private val view: ProfileFragmentViewModel by viewModels()

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)


        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UseRequireInsteadOfGet", "SetTextI18n", "MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_home, container, false)



            val b = v.findViewById< CardView>(R.id.gotoDashboard)
            b.setOnClickListener {

                val fragmentTransaction = fragmentManager!!.beginTransaction()
                fragmentTransaction.replace(R.id.frame_layout, analysis())
                fragmentTransaction.commit()

            }


            val timeText: TextView = v.findViewById(R.id.time)
            val m = LocalTime.now()
            val formater = DateTimeFormatter.ofPattern("hh:mm a")
            val t = m.format(formater)

            timeText.setText(t)


            val sharedPreferences = requireContext().getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)

        val token = sharedPreferences.getString("ACCESS_TOKEN", null)


        var text = v.findViewById<TextView>(R.id.textView10)

        if (token != null) {
            view.fetchUserProfile(token)
        } else {
            Toast.makeText(requireContext(), "Token not found. Please log in again.", Toast.LENGTH_SHORT).show()
        }

        view.userProfile.observe(viewLifecycleOwner) { userProfile ->
            if (userProfile != null) {


                text.text = "${userProfile.firstName} ${userProfile.lastName}"

            }










        }

return v
    }





    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment home.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            home().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}



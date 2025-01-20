package com.example.watertracker

import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
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
import mobile.kotlinexamples.fragments.com.example.watertracker.fullDayWaterIntakeViewModel
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
    private val fullDayWaterIntake: fullDayWaterIntakeViewModel by viewModels()

    private lateinit var passwordStrengthBar: CardView
    private lateinit var passwordStrengthLabel: TextView
    private lateinit var progressParent: CardView

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
//
//                val fragmentTransaction = fragmentManager!!.beginTransaction()
//                fragmentTransaction.replace(R.id.frame_layout, analysis())
//                fragmentTransaction.commit()

                val intent = Intent(requireContext(), logWaterIntake::class.java)
                startActivity(intent)

            }


            val timeText: TextView = v.findViewById(R.id.time)
            val m = LocalTime.now()
            val formater = DateTimeFormatter.ofPattern("hh:mm a")
            val t = m.format(formater)

            timeText.setText(t)


            val sharedPreferences = requireContext().getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)

        val token = sharedPreferences.getString("ACCESS_TOKEN", null)


        var text = v.findViewById<TextView>(R.id.textView10)
        var waterConsumedText=v.findViewById<TextView>(R.id.waterConsumed)
        var target=v.findViewById<TextView>(R.id.targetText)

        passwordStrengthBar = v.findViewById(R.id.progres)
//        passwordStrengthLabel = v.findViewById(R.id.passwordStrengthLabel)
        progressParent=v.findViewById<CardView>(R.id.textView11)


        if (token != null) {
            view.fetchUserProfile(token)
            fullDayWaterIntake.updateFullDayWaterIntake(token)

        } else {
            Toast.makeText(requireContext(), "Token not found. Please log in again.", Toast.LENGTH_SHORT).show()
        }
        var tar: Double=0.0;
        view.userProfile.observe(viewLifecycleOwner) { userProfile ->
            if (userProfile != null) {

                val first: String = userProfile.firstName.toString()
                val last: String = userProfile.lastName.toString()

                if(userProfile.weight==null||userProfile.age==null){
                    tar=getDailyTarget(0.0,0)
                }
                else{
                    tar=getDailyTarget(userProfile.weight,userProfile.age)
                }



                text.text = "${first[0].uppercaseChar()}${first.substring(1)} ${last[0].uppercaseChar()}${last.substring(1)}"
                target.text="${tar}ml"


            }
        }


        fullDayWaterIntake.getFullDayUpdate.observe(viewLifecycleOwner){ waterIntake ->

            if(waterIntake==null){
                waterConsumedText.text="0ml"
                v.findViewById<TextView>(R.id.textView5).text="You got 0% of today’s goal, keep focus on your health!"
                updatePasswordStrength(0.0)

            }
            else{
                waterConsumedText.text="${waterIntake.waterConsumed}ml"
                var progress =if(waterIntake.waterConsumed>=tar){
                    100.0
                }
                else{
                    waterIntake.waterConsumed*100/tar
                }


                if(progress!=100.0){
                    v.findViewById<TextView>(R.id.textView5).text="You got ${progress.toInt()}% of today’s goal, keep focus on your health!"
                }
                else{
                    v.findViewById<TextView>(R.id.textView5).text="Hurray! You got 100% of today’s goal, keep focus on your health!"

                }

                updatePasswordStrength(progress)


                val timeString = waterIntake.waterIntake.last().time


                val inputFormatter = DateTimeFormatter.ofPattern("HH:mm:ss[.SSS]")
                val parsedTime = LocalTime.parse(timeString, inputFormatter)


                val outputFormatter = DateTimeFormatter.ofPattern("hh:mm a")
                val formattedTime = parsedTime.format(outputFormatter)


                v.findViewById<TextView>(R.id.timeStamp).text = formattedTime
                v.findViewById<TextView>(R.id.lastConsumed).text = "${waterIntake.waterIntake.last().waterConsumed}ml"






            }



        }



return v
    }

    fun getDailyTarget(weight: Double,age: Int): Double{
        var baseTarget = weight * 35;

        if (age > 50) {
            baseTarget -= 200;
        }

return baseTarget;
    }




    private fun updatePasswordStrength(progress: Double) {
        passwordStrengthBar.visibility = View.VISIBLE
//        passwordStrengthLabel.visibility = View.VISIBLE
        progressParent.visibility = View.VISIBLE

        val (targetProgress, targetColor, labelText) = when (progress) {
            in 0.0..32.0 -> Triple(progress/100, Color.parseColor("#E33629"), "Your Password is too weak")
            in 33.0..99.0 -> Triple(
                progress/100,
                Color.parseColor("#F8BD00"),
                "Your Password could be stronger"
            )

            else -> Triple(progress/100, Color.parseColor("#38b5ee"), "Your Password is strong")
        }

        val parentWidth = progressParent.width
        val targetWidth = (parentWidth * targetProgress).toInt()





        val widthAnimator =
            ValueAnimator.ofInt(passwordStrengthBar.layoutParams.width, targetWidth).apply {
                duration = 1000000
                interpolator = android.view.animation.DecelerateInterpolator()
                addUpdateListener { animation ->
                    val animatedWidth = animation.animatedValue as Int
                    passwordStrengthBar.layoutParams = passwordStrengthBar.layoutParams.apply {
                        width = animatedWidth
                    }
                    passwordStrengthBar.requestLayout()

                }
            }


        val currentColor = (passwordStrengthBar.backgroundTintList?.defaultColor ?: Color.GRAY)
        val colorAnimator =
            ValueAnimator.ofObject(ArgbEvaluator(), currentColor, targetColor).apply {
                duration = 1000000
                addUpdateListener { animator ->
                    val animatedColor = animator.animatedValue as Int
//                    passwordStrengthLabel.textColors
                    passwordStrengthBar.backgroundTintList = ColorStateList.valueOf(animatedColor)
//                    passwordStrengthLabel.setTextColor(animatedColor)

                }
            }






        AnimatorSet().apply {
            playTogether(widthAnimator, colorAnimator)
            start()
        }


//        passwordStrengthLabel.text = labelText


//        if (targetProgress == 1.0f) {
//            passwordStrengthLabel.postDelayed({
//                passwordStrengthBar.visibility = View.GONE
//                passwordStrengthLabel.visibility = View.GONE
//                progressParent.visibility = View.GONE
//            }, 400)
//
//        }


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



package com.example.watertracker

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioGroup
import androidx.fragment.app.activityViewModels
import mobile.kotlinexamples.fragments.Communicator



class editProfile : Fragment() {



    companion object {
        fun newInstance() = editProfile()
    }

    private val viewModel: EditProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    @SuppressLint("UseRequireInsteadOfGet", "MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView=inflater.inflate(R.layout.fragment_edit_profile2, container, false)


           var btn=rootView.findViewById<Button>(R.id.updtBtn)

        var editText=rootView.findViewById<EditText>(R.id.editFirstName)
        var editLast=rootView.findViewById<EditText>(R.id.editLastName)
        var editMail=rootView.findViewById<EditText>(R.id.editEmail)
        var editAge=rootView.findViewById<EditText>(R.id.editAge)
        val profileImageView: ImageView = rootView.findViewById(R.id.editProfileImage)
        btn.setOnClickListener {
            //   val bundle = Bundle()
//            bundle.putString("first", editText.text.toString())
//            bundle.putString("last", editLast.text.toString())
//            bundle.putString("mail", editMail.text.toString())
//            bundle.putString("age",editAge.text.toString())

            /*     var genderRadioGroup :RadioGroup=rootView.findViewById(R.id.radioSex)
            genderRadioGroup.setOnCheckedChangeListener { _, checkedId ->
                val gender = when (checkedId) {
                    R.id.radioMale -> "Male"
                    R.id.radioFemale -> "Female"
                    else -> "Other"
                }



                val emojiResource = when (gender) {
                    "Male" -> R.drawable.male_user
                    "Female" -> R.drawable.profile_pic
                    else -> R.drawable.others_user
                }
                profileImageView.setImageResource(emojiResource)
            }

            sharedViewModel.firstName.value = editText.text.toString()
            sharedViewModel.lastName.value = editLast.text.toString()
            sharedViewModel.email.value = editMail.text.toString()
            sharedViewModel.age.value = editAge.text.toString()

            val transaction = fragmentManager!!.beginTransaction()
            val fragmentTwo = profileFraagment()
           // fragmentTwo.arguments = bundle
            transaction.replace(R.id.frame_layout, fragmentTwo)

            transaction.commit()
        */
        }

        return rootView
    }
}


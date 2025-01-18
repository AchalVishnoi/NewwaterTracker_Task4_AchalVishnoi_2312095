

package mobile.kotlinexamples.fragments
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.replace
import com.example.watertracker.R
import com.example.watertracker.editProfile
import com.example.watertracker.profileFraagment



class PassDataFragmentsMainClass : AppCompatActivity(), Communicator {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passdatafragmentsmain)
        val firstFragment = editProfile()
        supportFragmentManager.beginTransaction().replace(R.id.frame_layout , firstFragment)
            .commit()
    }
    override fun passData(editTextInput: String, edtitquantity:String, editPrice:String,editAge:String) {
        val bundle = Bundle()
        bundle.putString("first", editTextInput)
        bundle.putString("last", edtitquantity)
        bundle.putString("mail", editPrice)
        bundle.putString("age",editAge)

        val transaction = this.supportFragmentManager.beginTransaction()
        val fragmentTwo = profileFraagment()
        fragmentTwo.arguments = bundle
        transaction.replace(R.id.frame_layout, fragmentTwo)

        transaction.commit()
    }
}



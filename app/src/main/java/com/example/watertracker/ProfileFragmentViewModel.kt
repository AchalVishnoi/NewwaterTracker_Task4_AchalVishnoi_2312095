package mobile.kotlinexamples.fragments.com.example.watertracker

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import backend.getUserProfile
import mobile.kotlinexamples.fragments.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfileFragmentViewModel : ViewModel() {

    private val _userProfile = MutableLiveData<getUserProfile?>()
    val userProfile: LiveData<getUserProfile?> get() = _userProfile

    fun fetchUserProfile(token: String) {
        Log.d("ProfileFragmentViewModel", "Fetching user profile with token: $token")
        RetrofitInstance.apiService.getUserProfile("Bearer $token")
            .enqueue(object : Callback<getUserProfile> {
                override fun onResponse(call: Call<getUserProfile>, response: Response<getUserProfile>) {
                    if (response.isSuccessful && response.body() != null) {
                        Log.d("ProfileFragmentViewModel", "User profile fetched successfully.")
                        _userProfile.postValue(response.body())
                    } else {
                        Log.e("ProfileFragmentViewModel", "Failed to fetch user profile. Response code: ${response.code()}")
                        _userProfile.postValue(null)
                    }
                }

                override fun onFailure(call: Call<getUserProfile>, t: Throwable) {
                    Log.e("ProfileFragmentViewModel", "Network error: ${t.message}")
                    _userProfile.postValue(null)
                }
            })
    }
}

package mobile.kotlinexamples.fragments.com.example.watertracker

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import backend.fullDayWaterIntake
import backend.getUserProfile
import mobile.kotlinexamples.fragments.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class fullDayWaterIntakeViewModel : ViewModel() {

    private val _getFullDayUpdate = MutableLiveData<fullDayWaterIntake?>()
    val getFullDayUpdate: LiveData<fullDayWaterIntake?> get() = _getFullDayUpdate

    fun updateFullDayWaterIntake(token:String) {


        Log.d("ProfileFragmentViewModel", "Fetching user profile with token: $token")
        RetrofitInstance.apiService.getFulDayWaterIntake("Bearer $token")
            .enqueue(object : Callback<fullDayWaterIntake> {
                override fun onResponse(call: Call<fullDayWaterIntake>, response: Response<fullDayWaterIntake>) {
                    if (response.isSuccessful && response.body() != null) {
                        Log.d("ProfileFragmentViewModel", "User profile fetched successfully.")
                        _getFullDayUpdate.postValue(response.body())
                    } else {
                        Log.e("ProfileFragmentViewModel", "Failed to fetch user profile. Response code: ${response.code()}")
                        _getFullDayUpdate.postValue(null)
                    }
                }

                override fun onFailure(call: Call<fullDayWaterIntake>, t: Throwable) {
                    Log.e("ProfileFragmentViewModel", "Network error: ${t.message}")
                    _getFullDayUpdate.postValue(null)
                }
            })
    }
}
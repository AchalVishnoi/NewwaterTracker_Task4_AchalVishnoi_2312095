package mobile.kotlinexamples.fragments

import backend.addDetailRequest
import backend.createUserResponse
import backend.create_user
import backend.create_user_response
import backend.fullDayWaterIntake
import backend.getUserProfile
import backend.logWaterIntakeRequest
import backend.logWaterIntakeResponse
import backend.loginRequest
import backend.response
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT


interface ApiService {



    @POST("/users/signup")
    fun createUser(@Body createUser: create_user): retrofit2.Call<createUserResponse>

    @POST("/users/signin")
    fun signInUser(@Body login: loginRequest): Call<create_user_response>

    @PUT("/users/add")
    fun addDetails(
        @Body addDetailRequest: addDetailRequest
    ): Call<response>

    @GET("/api/users/profile")
    fun getUserProfile(
        @Header("Authorization") token: String
    ): Call<getUserProfile>

    @POST("/api/users/water/log")
    fun logWaterIntake(
        @Header("Authorization") token: String,
        @Body logWaterIntakeRequest: logWaterIntakeRequest
    ) : Call<logWaterIntakeResponse>


    @GET("/api/users/water/getTodayRecord")
    fun getFulDayWaterIntake(
        @Header("Authorization") token: String
    ): Call<fullDayWaterIntake>



}
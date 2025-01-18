package backend

import java.time.LocalTime

data class addDetailRequest(
    val userId : Int,
    val age: Int,
    val gender: String,
    val sleepTime: String,
    val wakeUpTime: String,
    val weight: Double,
    val height: Double
)
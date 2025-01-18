package backend

import java.time.LocalTime
import com.fasterxml.jackson.annotation.JsonFormat

data class getUserProfile(
    val id: Long?,
    val firstName: String?,
    val lastName: String?,
    val email: String?,
    val age: Int?,
    val gender: String?,

    val wakeUpTime: String,
//    @JsonFormat(pattern = "hh:mm a")
    val sleepTime: String,
    val weight: Double?,
    val isVerified: Boolean?,
    val detailsComplete: Boolean?,
    val height: Double?,
    val bmi: Double?

)
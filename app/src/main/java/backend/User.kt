package backend

data class User(
    val age: Int,
    val bmi: Double,
    val dailyTarget: Double,
    val detailsComplete: Boolean,
    val email: String,
    val firstName: String,
    val gender: String,
    val height: Double,
    val id: Int,
    val lastName: String,
    val password: String,
    val sleepTime: String,
    val verified: Boolean,
    val wakeUpTime: String,
    val weight: Double
)
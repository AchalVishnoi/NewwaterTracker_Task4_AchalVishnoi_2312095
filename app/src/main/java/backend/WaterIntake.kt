package backend

data class WaterIntake(
    val date: String,
    val id: Int,
    val time: String,
    val user: User,
    val waterConsumed: Int
)
package backend

data class fullDayWaterIntake(
    val date: String,
    val id: Int,
    val user: User,
    val waterConsumed: Double,
    val waterIntake: List<WaterIntake>
)
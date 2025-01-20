package backend

data class logWaterIntakeResponse(
    val consumed: Double,
    val message: String,
    val progress: Double,
    val remaining: Double,
    val target: Double
)
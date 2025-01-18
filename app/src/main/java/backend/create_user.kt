package backend

data class create_user(

    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
)
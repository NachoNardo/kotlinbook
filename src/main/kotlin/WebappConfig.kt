package kotlinbook

data class WebappConfig(
    val httpPort: Int,
    val dbUsername: String,
    val dbPassword: String?
)
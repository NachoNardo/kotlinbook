package kotlinbook

import com.typesafe.config.ConfigFactory
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.slf4j.LoggerFactory
import kotlin.reflect.full.declaredMemberProperties

val log = LoggerFactory.getLogger("kotlinbook.Main")!!

fun main() {
    val env = System.getenv("KOTLINBOOK_ENV") ?: "local"
    val config = createAppConfig(env)
    log.debug("Starting application...")
    log.debug("Application runs in the environment $env")
    val secretsRegex = "password|secret|key"
        .toRegex(RegexOption.IGNORE_CASE)

    log.debug("Configuration loaded successfully: ${
        WebappConfig::class.declaredMemberProperties
            .sortedBy { it.name }.joinToString(separator = "\n") {
                if (secretsRegex.containsMatchIn(it.name)) {
                    "${it.name} = ${it.get(config).toString().take(2)}*****"
                } else {
                    "${it.name} = ${it.get(config)}"
                }
            }
    }")

    embeddedServer(Netty, port = config.httpPort) {
        createKtorApplication()
    }.start(wait = true)
}

fun createAppConfig(env: String) =
    ConfigFactory
        .parseResources("app-${env}.conf")
        .withFallback(ConfigFactory.parseResources("app.conf"))
        .resolve()
        .let {
            WebappConfig(
                httpPort = it.getInt("httpPort"),
                dbUsername = it.getString("dbUsername"),
                dbPassword = it.getString("dbPassword")
            )
        }

fun Application.createKtorApplication() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            kotlinbook.log.error("An unknown error occurred", cause)

            call.respondText(
                text = "500: $cause",
                status = HttpStatusCode.InternalServerError
            )
        }
    }

    routing {
        get("/") {
            call.respondText("Hello, World!")
        }
    }
}
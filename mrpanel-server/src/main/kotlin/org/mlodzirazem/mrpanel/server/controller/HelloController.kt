package org.mlodzirazem.mrpanel.server.controller
import jakarta.servlet.http.HttpServletResponse
import jakarta.servlet.http.Cookie
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.web.bind.annotation.*
import java.security.MessageDigest
import java.util.Date
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm

@RestController
class HelloController(
    @Autowired private val jdbcTemplate: JdbcTemplate
) {

    @GetMapping("/api/hello")
    fun hello(): String = "Hello, API!"

    data class LoginRequest(val login: String, val password: String)
    data class LoginResponse(val success: Boolean)

    @PostMapping("/api/login")
    fun login(
        @RequestBody req: LoginRequest,
        response: HttpServletResponse
    ): LoginResponse {
        val sql = "SELECT hashed_pass FROM \"user1\" WHERE login = ?"
        val dbHash = jdbcTemplate.query(sql, arrayOf(req.login)) { rs, _ -> rs.getString("hashed_pass") }
            .firstOrNull() ?: return LoginResponse(false)

        val inputHash = hashPassword(req.password)
        if (inputHash == dbHash) {
            val algorithm = Algorithm.HMAC256("tomato")
            val jwt = JWT.create()
                .withSubject(req.login)
                .withIssuedAt(Date())
                .withExpiresAt(Date(System.currentTimeMillis() + 3600_000)) // 1 hour expiry
                .sign(algorithm)

            val cookie = Cookie("jwt", jwt)
            cookie.isHttpOnly = true
            cookie.path = "/"
            cookie.maxAge = 3600 // 1 hour
            response.addCookie(cookie)
            return LoginResponse(true)
        }
        return LoginResponse(false)
    }

    private fun hashPassword(password: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(password.toByteArray())
        return digest.joinToString("") { "%02x".format(it) }
    }
}

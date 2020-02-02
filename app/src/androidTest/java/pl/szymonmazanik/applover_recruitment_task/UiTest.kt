package pl.szymonmazanik.applover_recruitment_task

import android.widget.Button
import androidx.test.espresso.Espresso.onView
import androidx.test.rule.ActivityTestRule
import com.google.android.material.textfield.TextInputEditText
import org.junit.Rule
import org.junit.Test
import pl.szymonmazanik.applover_recruitment_task.model.entity.request.LoginRequest
import pl.szymonmazanik.applover_recruitment_task.ui.LoginActivity

class UiTest {
    @get:Rule
    var rule: ActivityTestRule<LoginActivity> =
        ActivityTestRule(LoginActivity::class.java)

    private val loginRequest = LoginRequest("login@applover.pl", "password123")

    @Test
    fun loginWithTestInput() {
        val emailInput = rule.activity.findViewById<TextInputEditText>(R.id.email_input)
        val passwordInput = rule.activity.findViewById<TextInputEditText>(R.id.password_input)
        val loginButton = rule.activity.findViewById<Button>(R.id.login_button)

        emailInput.setText(loginRequest.email)
        passwordInput.setText(loginRequest.password)

        loginButton.performClick()

        onView(R.id.)
    }

}
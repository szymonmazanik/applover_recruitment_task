package pl.szymonmazanik.applover_recruitment_task

import org.junit.Assert.assertFalse
import org.junit.Test
import pl.szymonmazanik.applover_recruitment_task.model.entity.request.LoginRequest
import pl.szymonmazanik.applover_recruitment_task.utils.extensions.isInputDataValid
import pl.szymonmazanik.applover_recruitment_task.utils.extensions.isValidEmail

/**
 * This needs to be in androidTest because we are using Android's util package
 */
class ValidationTest {

    private val correctLogin = LoginRequest("login@applover.pl", "password123")
    private val badLogin = LoginRequest("Bad Email", "password123")

    @Test
    fun testEmailValidation() {
        assert(correctLogin.email.isValidEmail())
        assertFalse(badLogin.email.isValidEmail())
    }

    @Test
    fun testInputDataValidation() {
        assert(correctLogin.isInputDataValid())
        assertFalse(badLogin.isInputDataValid())
    }
}
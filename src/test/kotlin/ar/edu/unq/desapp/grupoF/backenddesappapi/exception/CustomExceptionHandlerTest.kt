package ar.edu.unq.desapp.grupoF.backenddesappapi.exception
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.http.HttpStatus
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.context.request.WebRequest

class CustomExceptionHandlerTest {

    @Test
    fun `handleValidationExceptions returns BAD_REQUEST status and error details`() {
        val exceptionHandler = CustomExceptionHandler()
        val ex = mock(MethodArgumentNotValidException::class.java)
        val request = mock(WebRequest::class.java)
        val fieldError = FieldError("objectName", "field", "defaultMessage")
        val bindingResult = mock(BindingResult::class.java)

        `when`(ex.bindingResult).thenReturn(bindingResult)
        `when`(bindingResult.fieldErrors).thenReturn(listOf(fieldError))

        val responseEntity = exceptionHandler.handleValidationExceptions(ex, request)

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.statusCode)
        assertEquals(listOf("field: defaultMessage"), responseEntity.body?.get("fields") ?: listOf<String>())
        assertEquals("Invalid input in the following fields", responseEntity.body?.get("message") ?: "default value")
    }

    @Test
    fun `handleValidationExceptions returns BAD_REQUEST status and multiple error details`() {
        val exceptionHandler = CustomExceptionHandler()
        val ex = mock(MethodArgumentNotValidException::class.java)
        val request = mock(WebRequest::class.java)
        val fieldError1 = FieldError("objectName", "field1", "defaultMessage1")
        val fieldError2 = FieldError("objectName", "field2", "defaultMessage2")
        val bindingResult = mock(BindingResult::class.java)

        `when`(ex.bindingResult).thenReturn(bindingResult)
        `when`(bindingResult.fieldErrors).thenReturn(listOf(fieldError1, fieldError2))

        val responseEntity = exceptionHandler.handleValidationExceptions(ex, request)

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.statusCode)
        assertTrue((responseEntity.body?.get("fields") as List<*>).contains("field1: defaultMessage1"))
        assertTrue((responseEntity.body?.get("fields") as List<*>).contains("field2: defaultMessage2"))
        assertEquals("Invalid input in the following fields", responseEntity.body?.get("message") ?: "default value")
    }
}
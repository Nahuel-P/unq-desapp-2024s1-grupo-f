package ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto

import jakarta.validation.Validation
import jakarta.validation.ValidatorFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class UserCreateRequestDTOTest {

    private val validatorFactory: ValidatorFactory = Validation.buildDefaultValidatorFactory()
    private val validator = validatorFactory.validator

    @Test
    fun `valid DTO should pass all validations`() {
        val dto = UserCreateRequestDTO(
            firstName = "Miguel Angel",
            lastName = "Borja",
            email = "el.colibri09@gmail.com",
            address = "Av. Siempre Viva 742",
            password = "Contraseña1!",
            cvu = "1234567890123456789012",
            walletAddress = "12345678"
        )

        val violations = validator.validate(dto)
        assertTrue(violations.isEmpty())
    }

    @Test
    fun `DTO with short first name should fail validation`() {
        val dto = UserCreateRequestDTO(
            firstName = "Mi",
            lastName = "Borja",
            email = "el.colibri09@gmail.com",
            address = "Av. Siempre Viva 742",
            password = "Contraseña1!",
            cvu = "1234567890123456789012",
            walletAddress = "12345678"
        )

        val violations = validator.validate(dto)
        assertEquals(1, violations.size)
        assertEquals("First name must be between 3 and 30 characters", violations.first().message)
    }

    @Test
    fun `DTO with invalid email should fail validation`() {
        val dto = UserCreateRequestDTO(
            firstName = "Miguel Angel",
            lastName = "Borja",
            email = "el.colibri09",
            address = "Av. Siempre Viva 742",
            password = "Contraseña1!",
            cvu = "1234567890123456789012",
            walletAddress = "12345678"
        )

        val violations = validator.validate(dto)
        assertEquals(1, violations.size)
        assertEquals("Email should be valid", violations.first().message)
    }

    @Test
    fun `DTO with weak password should fail validation`() {
        val dto = UserCreateRequestDTO(
            firstName = "Miguel Angel",
            lastName = "Borja",
            email = "el.colibri09@gmail.com",
            address = "Av. Siempre Viva 742",
            password = "pass",
            cvu = "1234567890123456789012",
            walletAddress = "12345678"
        )

        val violations = validator.validate(dto)
        assertEquals(1, violations.size)
        assertEquals(
            "Password must contain at least 1 lowercase letter, 1 uppercase letter, 1 special character and be at least 6 characters long",
            violations.first().message
        )
    }

    @Test
    fun `DTO with short CVU should fail validation`() {
        val dto = UserCreateRequestDTO(
            firstName = "Miguel Angel",
            lastName = "Borja",
            email = "el.colibri09@gmail.com",
            address = "Av. Siempre Viva 742",
            password = "Contraseña1!",
            cvu = "12345678",
            walletAddress = "12345678"
        )

        val violations = validator.validate(dto)
        assertEquals(1, violations.size)
        assertEquals("CVU must be 22 characters", violations.first().message)
    }

    @Test
    fun `DTO with short wallet address should fail validation`() {
        val dto = UserCreateRequestDTO(
            firstName = "Miguel Angel",
            lastName = "Borja",
            email = "el.colibri09@gmail.com",
            address = "Av. Siempre Viva 742",
            password = "Contraseña1!",
            cvu = "1234567890123456789012",
            walletAddress = "1234"
        )

        val violations = validator.validate(dto)
        assertEquals(1, violations.size)
        assertEquals("Wallet address must be 8 characters", violations.first().message)
    }
}
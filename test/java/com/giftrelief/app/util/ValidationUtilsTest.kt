package com.giftrelief.app.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertNotNull
import org.junit.Test

class ValidationUtilsTest {

    @Test
    fun `blank email is rejected`() {
        assertNotNull(ValidationUtils.validateEmail(""))
    }

    @Test
    fun `malformed email is rejected`() {
        assertNotNull(ValidationUtils.validateEmail("not-an-email"))
    }

    @Test
    fun `valid email passes`() {
        assertNull(ValidationUtils.validateEmail("user@example.com"))
    }

    @Test
    fun `short password is rejected`() {
        assertNotNull(ValidationUtils.validatePassword("abc1"))
    }

    @Test
    fun `password without a digit is rejected`() {
        assertNotNull(ValidationUtils.validatePassword("abcdefgh"))
    }

    @Test
    fun `strong password passes`() {
        assertNull(ValidationUtils.validatePassword("password123"))
    }

    @Test
    fun `mismatched confirm password is rejected`() {
        assertNotNull(ValidationUtils.validateConfirmPassword("password123", "password124"))
    }

    @Test
    fun `matching confirm password passes`() {
        assertNull(ValidationUtils.validateConfirmPassword("password123", "password123"))
    }

    @Test
    fun `blank donation amount is rejected`() {
        assertNotNull(ValidationUtils.validateDonationAmount(""))
    }

    @Test
    fun `non numeric donation amount is rejected`() {
        assertNotNull(ValidationUtils.validateDonationAmount("abc"))
    }

    @Test
    fun `zero or negative donation amount is rejected`() {
        assertNotNull(ValidationUtils.validateDonationAmount("0"))
        assertNotNull(ValidationUtils.validateDonationAmount("-5"))
    }

    @Test
    fun `valid donation amount passes`() {
        assertNull(ValidationUtils.validateDonationAmount("250"))
    }
}

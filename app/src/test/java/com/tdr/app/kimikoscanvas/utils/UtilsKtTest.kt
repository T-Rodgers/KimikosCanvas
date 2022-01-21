package com.tdr.app.kimikoscanvas.utils

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.*
import org.junit.Test

class UtilsKtTest {

    @Test
    fun convertPrice_toUsCurrencyFormat() {
        val price = 30

        val result = convertToCurrency(price)

        assertThat(result, `is`("$30.00"))

    }
}
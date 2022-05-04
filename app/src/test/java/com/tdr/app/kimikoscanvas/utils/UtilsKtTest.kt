package com.tdr.app.kimikoscanvas.utils

import androidx.test.filters.SmallTest
import org.junit.Assert.assertEquals
import org.junit.Test


@SmallTest
class UtilsKtTest {

    @Test
    fun metersToMiles_ConversionTest(){

        val meters = 10000.00
        val miles = convertMetersToMiles(meters)  // meters * .000621 = miles

        assertEquals(6, miles)
    }


}
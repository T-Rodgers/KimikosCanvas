package com.tdr.app.kimikoscanvas.utils

import kotlin.math.roundToInt


fun convertMetersToMiles(meters: Double): Int {

    return (meters * .000621).roundToInt()

}

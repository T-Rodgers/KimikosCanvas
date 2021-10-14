package com.tdr.app.kimikoscanvas.utils

import java.text.DecimalFormat

fun convertToCurrency(price: Int): String {
    val format = DecimalFormat("$#,###.00")
    format.isDecimalSeparatorAlwaysShown = false
    return format.format(price).toString()
}
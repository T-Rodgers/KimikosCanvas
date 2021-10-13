package com.tdr.app.kimikoscanvas

import java.text.DecimalFormat

//TODO: (1) Convert
fun Int.convertToCurrency(): String {
    val format = DecimalFormat("$#,###.00")
    format.isDecimalSeparatorAlwaysShown = false
    return format.format(this).toString()
}
package com.exactaworks.exactabank.extensions

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

class Extensions {
    companion object {
        public val CURRENCY_INSTANCE = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
    }
}

fun BigDecimal.toRealString() : String {
    return Extensions.CURRENCY_INSTANCE.format(this)
}
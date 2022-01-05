package com.specikman.petbest.common

import java.text.NumberFormat
import java.util.*

class ToMoneyFormat {
    companion object {
        fun toMoney(number: Long): String = NumberFormat.getCurrencyInstance().run {
            maximumFractionDigits = 0
            currency = Currency.getInstance("VND")
            format(number)
        }
    }
}
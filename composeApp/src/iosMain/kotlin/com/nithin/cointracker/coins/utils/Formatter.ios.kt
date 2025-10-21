package com.nithin.cointracker.coins.utils

import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterDecimalStyle

actual fun formateDouble(amount: Double, showDecimal: Boolean): String {
    val numberFormater = NSNumberFormatter()
    numberFormater.numberStyle = NSNumberFormatterDecimalStyle
    when{
        showDecimal.not() -> {
            numberFormater.minimumFractionDigits = 0.toULong()
            numberFormater.maximumFractionDigits = 0.toULong()
        }
        amount >=0.01 ->{
            numberFormater.minimumFractionDigits = 2.toULong()
            numberFormater.maximumFractionDigits = 2.toULong()
        }
        else ->{
            numberFormater.minimumFractionDigits = 8.toULong()
            numberFormater.maximumFractionDigits = 8.toULong()
        }
    }
    val formatedAmount = numberFormater.stringFromNumber(NSNumber(amount))
    return if (formatedAmount!=null) "$ $formatedAmount" else ""
}

actual fun formateCurrencyUnit(amount: Double, symbol: String): String {
    val numberFormatter = NSNumberFormatter()
    numberFormatter.numberStyle = NSNumberFormatterDecimalStyle
    numberFormatter.minimumFractionDigits = 8.toULong()
    numberFormatter.maximumFractionDigits = 8.toULong()

    return numberFormatter.stringFromNumber(NSNumber(amount)) + " $symbol"
}

actual fun formatePercentage(amount: Double): String {
    val numberFormatter = NSNumberFormatter()
    numberFormatter.numberStyle = NSNumberFormatterDecimalStyle
    numberFormatter.minimumFractionDigits = 2.toULong()
    numberFormatter.maximumFractionDigits = 2.toULong()
    val prefix = if (amount>=0) "+" else ""

    return prefix + numberFormatter.stringFromNumber(NSNumber(amount)) + " %"
}
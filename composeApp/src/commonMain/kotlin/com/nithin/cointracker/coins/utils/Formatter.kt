package com.nithin.cointracker.coins.utils

expect fun formateDouble(
    amount : Double,
    showDecimal : Boolean = true
) : String

expect fun formateCurrencyUnit(
    amount : Double,
    symbol : String
) : String

expect fun formatePercentage(
    amount: Double
) : String
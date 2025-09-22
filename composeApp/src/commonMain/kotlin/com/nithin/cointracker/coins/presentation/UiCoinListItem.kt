package com.nithin.cointracker.coins.presentation

import cointracker.composeapp.generated.resources.Res

data class UiCoinListItem(
    val id : String,
    val name : String,
    val symbol : String,
    val iconUrl : String,
    val formattedPrice : String,
    val formattedChange : String,
    val isPositive : Boolean
)
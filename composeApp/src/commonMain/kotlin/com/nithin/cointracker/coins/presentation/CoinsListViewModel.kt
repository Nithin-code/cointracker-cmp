package com.nithin.cointracker.coins.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nithin.cointracker.coins.domain.GetCoinsListUseCase
import com.nithin.cointracker.shared.domain.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class CoinsListViewModel(
    private val getCoinsListUseCase: GetCoinsListUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CoinState())

    val state = _state
        .onStart {
            getAllCoins()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CoinState()
        )

    private suspend fun getAllCoins(){
        val coinResponse = getCoinsListUseCase.execute()
        when(coinResponse){
            is Result.Error -> {
                _state.update {
                    CoinState(
                        error = null
                    )
                }
            }
            is Result.Success -> {
                _state.update {
                    CoinState(
                        coins = coinResponse.data.map { coinItem->
                            UiCoinListItem(
                                id = coinItem.coin.id,
                                name = coinItem.coin.name,
                                symbol = coinItem.coin.symbol,
                                iconUrl = coinItem.coin.iconUrl,
                                isPositive = coinItem.change >= 0,
                                formattedPrice = coinItem.price.toString(),
                                formattedChange = coinItem.change.toString()
                            )
                        }
                    )
                }

            }
        }
    }
}
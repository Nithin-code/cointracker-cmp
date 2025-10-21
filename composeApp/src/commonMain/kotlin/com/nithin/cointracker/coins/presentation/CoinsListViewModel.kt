package com.nithin.cointracker.coins.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cointracker.composeapp.generated.resources.Res
import cointracker.composeapp.generated.resources.error_serialization
import com.nithin.cointracker.coins.domain.GetCoinsListUseCase
import com.nithin.cointracker.coins.domain.GetPriceHistoryUseCase
import com.nithin.cointracker.coins.utils.formateDouble
import com.nithin.cointracker.coins.utils.formatePercentage
import com.nithin.cointracker.coins.utils.toUIText
import com.nithin.cointracker.shared.domain.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CoinsListViewModel(
    private val getCoinsListUseCase: GetCoinsListUseCase,
    private val getPriceHistoryUseCase: GetPriceHistoryUseCase
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

    private suspend fun getAllCoins() {
        val coinResponse = getCoinsListUseCase.execute()
        when (coinResponse) {
            is Result.Error -> {
                _state.update {
                    CoinState(
                        coins = emptyList(),
                        error = coinResponse.error.toUIText()
                    )
                }
            }

            is Result.Success -> {
                _state.update {
                    CoinState(
                        coins = coinResponse.data.map { coinItem ->
                            UiCoinListItem(
                                id = coinItem.coin.id,
                                name = coinItem.coin.name,
                                symbol = coinItem.coin.symbol,
                                iconUrl = coinItem.coin.iconUrl,
                                isPositive = coinItem.change >= 0,
                                formattedPrice = formateDouble(coinItem.price),
                                formattedChange = formatePercentage(coinItem.change)
                            )
                        }
                    )
                }

            }
        }
    }

    fun onCoinItemLongPressed(coinId: String) {
        _state.update {
            it.copy(
                chartState = UiChartState(
                    sparkLine = emptyList(),
                    isLoading = true
                )
            )
        }

        viewModelScope.launch {
            val priceHistory = getPriceHistoryUseCase.execute(coinId)

            when (priceHistory) {
                is Result.Error -> {
                    _state.update {
                        it.copy(
                            chartState = UiChartState(
                                sparkLine = emptyList(),
                                isLoading = false,
                                coinName = ""
                            )
                        )
                    }
                }

                is Result.Success -> {
                    _state.update {
                        it.copy(
                            chartState = UiChartState(
                                sparkLine = priceHistory.data.sortedBy { it.timeStamp }.map {
                                    it.price
                                },
                                isLoading = false,
                                coinName = _state.value.coins.find { it.id == coinId }?.name.orEmpty()
                            )
                        )
                    }
                }
            }
        }

    }

    fun onDismissChart(){
        _state.update {
            it.copy(
                chartState = null
            )
        }
    }
}
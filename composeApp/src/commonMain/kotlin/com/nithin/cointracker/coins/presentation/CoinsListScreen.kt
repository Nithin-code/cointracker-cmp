package com.nithin.cointracker.coins.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.nithin.cointracker.coins.presentation.components.PerformanceChat
import com.nithin.cointracker.theme.LocalCoinTrackerColorPalette
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CoinsListScreen(
    onCoinClicked : (String) -> Unit
){

//    val coinsListViewModel = viewModel<CoinsListViewModel>()

    val coinsListViewModel = koinViewModel<CoinsListViewModel>()
    val coinsState by coinsListViewModel.state.collectAsStateWithLifecycle()

    CoinListContent(
        state = coinsState,
        onCoinClicked = onCoinClicked,
        onDismissChart = {
            coinsListViewModel.onDismissChart()
        },
        onLongPressed = { coinId ->
            coinsListViewModel.onCoinItemLongPressed(coinId)
        }
    )


}

@Composable
fun CoinListContent(
    state: CoinState,
    onDismissChart : () -> Unit,
    onLongPressed : (String) -> Unit,
    onCoinClicked: (String) -> Unit
){

    Scaffold { paddingValues ->

        Box(modifier = Modifier
            .fillMaxSize()
            .padding(top = paddingValues.calculateTopPadding(), bottom = paddingValues.calculateBottomPadding())
            .padding(horizontal = 12.dp)
            .background(
                color = MaterialTheme.colorScheme.background
            )){

            if (state.chartState!=null){
                CoinChartDialog(
                    uiChartState = state.chartState,
                    onDismiss = {
                        onDismissChart.invoke()
                    }
                )
            }

            CoinsList(
                coins = state.coins,
                onCoinClicked = onCoinClicked,
                onLongPressed = onLongPressed
            )
        }

    }

}

@Composable
fun CoinsList(
    coins : List<UiCoinListItem>,
    onCoinClicked: (String) -> Unit,
    onLongPressed: (String) -> Unit
){
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
    ){

        if (coins.isEmpty()){
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                CircularProgressIndicator()
            }
        }else{
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {

                item {

                    Text(
                        text = "Top coins:",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        modifier = Modifier.padding(16.dp)
                    )

                }
                items(coins){ coin->
                    CoinListItem(
                        coin,
                        onCoinClicked = onCoinClicked,
                        onLongPressed = onLongPressed
                    )
                }

            }
        }
    }
}

@Composable
fun CoinListItem(
    coin : UiCoinListItem,
    onCoinClicked: (String) -> Unit,
    onLongPressed: (String) -> Unit
){

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
            .combinedClickable(
                onClick = {
                    onCoinClicked.invoke(coin.id)
                },
                onLongClick = {
                    onLongPressed.invoke(coin.id)
                }
            )
            .padding(16.dp)
    ) {

        AsyncImage(
            model = coin.iconUrl,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.padding(4.dp).clip(CircleShape).size(40.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                text = coin.name,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = coin.symbol,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize
            )

        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = coin.formattedPrice,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = MaterialTheme.typography.titleMedium.fontSize
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = coin.formattedChange,
                color = if (coin.isPositive) LocalCoinTrackerColorPalette.current.profitGreen else LocalCoinTrackerColorPalette.current.lossRed,
                fontSize = MaterialTheme.typography.titleSmall.fontSize
            )
        }

    }

}

@Composable
fun CoinChartDialog(
    uiChartState: UiChartState,
    onDismiss : () -> Unit
){

    Dialog(
        onDismissRequest = {onDismiss.invoke()},
        content = {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(24.dp))
                    .background(color = Color.Transparent),
                contentAlignment = Alignment.Center
            ){

                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .blur(radius = 20.dp)
                        .background(color = Color.White.copy(alpha = 0.1f))
                        .border(
                            width = 3.dp,
                            color = Color.White.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(24.dp)
                        )
                )

                // actual no blur content

                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "24 hr Price chart for ${uiChartState.coinName}",
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ){
                        if (uiChartState.isLoading){
                            CircularProgressIndicator(
                                modifier = Modifier.size(32.dp),
                                color = Color.White.copy(alpha = 0.3f)
                            )
                        }else{
                            PerformanceChat(
                                modifier = Modifier
                                    .matchParentSize()
                                    .padding(16.dp),
                                nodes = uiChartState.sparkLine,
                                profitColor = LocalCoinTrackerColorPalette.current.profitGreen,
                                lossColor = LocalCoinTrackerColorPalette.current.lossRed
                            )
                        }
                    }

                }
            }
        }
    )

}
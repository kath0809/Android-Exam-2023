import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import no.pgr208.knr2044.product_data.room.ShoppingCart
import no.pgr208.knr2044.screens.order_history.OrderHistoryViewModel
import no.pgr208.knr2044.ui.theme.CartPrice
import no.pgr208.knr2044.ui.theme.OHBG

@Composable
fun OrderHistoryScreen(
    viewModel: OrderHistoryViewModel,
    onBackButtonClick: () -> Unit = {},
) {
    val orderHistory = viewModel.orderHistory.value
    val gson = Gson()
    val itemsType = object : TypeToken<List<ShoppingCart>>() {}.type


    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { onBackButtonClick() }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Navigate Back"
                )
            }

            Text(
                modifier = Modifier
                    .padding(8.dp),
                text = " Your Order History",
                style = MaterialTheme.typography.titleLarge,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }

        Divider()

        LazyColumn {
            items(orderHistory.size) { index ->
                val order = orderHistory[index]
                val items = gson.fromJson<List<ShoppingCart>>(order.items, itemsType)

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = OHBG,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp, start = 3.dp),
                            text = "${order.date}",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Spacer(modifier = Modifier.padding(8.dp))
                        items.forEach { item ->
                            val firstLine = item.productTitle.take(30)
                            Text(
                                modifier = Modifier
                                    .padding(8.dp),
                                text = " - $firstLine" + " x ${item.quantity}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        Spacer(modifier = Modifier.padding(8.dp))
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp, end = 8.dp),
                            text = "Total price: $ ${order.totalPrice}",
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = androidx.compose.ui.text.style.TextAlign.End,
                            color = CartPrice
                        )
                    }
                }
            }
        }
    }
}
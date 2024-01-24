package no.pgr208.knr2044.screens.store_list

import OrderHistoryScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.sharp.List
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import no.pgr208.knr2044.R
import no.pgr208.knr2044.ui.theme.Background
import no.pgr208.knr2044.ui.theme.CallToAction
import no.pgr208.knr2044.ui.theme.SearchTextColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreListScreen(
    viewModel: StoreListViewModel,
    navigateToHistory: () -> Unit = {},
    onProductClick: (productId: Int) -> Unit = {},
    navigateToCart: () -> Unit = {},
    navigateToFavorites: () -> Unit = {},

) {

    val loading = viewModel.loading.collectAsState()
    val products = viewModel.filteredProducts.collectAsState()
    val searchFilter = viewModel.searchFilter.collectAsState()

    // Mens produktene laster inn, vises en progressbar(CircularProgressIndicator),
    // som er stylet til å vies i midten av skjermen.
    if (loading.value) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    // Header og søkefelt
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier
                    .padding(8.dp),
                text = "Everyday Store",
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black,
            )
            Row(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { viewModel.loadProducts() }
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Refresh products",
                        tint = Color.Black
                    )
                }
                IconButton(onClick = { navigateToCart() }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ShoppingCart,
                        contentDescription = "See your shopping cart",
                        tint = Color.DarkGray,
                    )
                }
                IconButton(onClick = { navigateToFavorites() }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Favorite,
                        contentDescription = "View your favorites",
                        tint = Color.Red,
                    )
                }
                IconButton(onClick = { navigateToHistory() }
                ) {
                    Icon(
                        imageVector = Icons.Default.List,
                        contentDescription = "View your order history",
                        tint = Color.DarkGray,
                        )
                }
            }
        }

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .border(2.dp, Color.LightGray, shape = RoundedCornerShape(8.dp)),
            value = searchFilter.value,
            onValueChange = { text -> viewModel.onFilterTextChanged(text) },
            colors = TextFieldDefaults.textFieldColors(
                textColor = SearchTextColor,
                containerColor = Color.White,
                ),
            textStyle = MaterialTheme.typography.titleMedium,
            label = { Text(text = "Search products...") },
        )

        // Main
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(products.value) { product ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { onProductClick(product.id) }
                        .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                        .border(1.dp, Color.LightGray, shape = RoundedCornerShape(8.dp)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .size(108.dp, 108.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        model = product.image,
                        alignment = Alignment.Center,
                        contentScale = ContentScale.Crop,
                        contentDescription = "Image of ${product.title}",
                        placeholder = painterResource(id = R.drawable.store_placeholder)
                    )

                    Column(
                        modifier = Modifier
                            .weight(1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            text = product.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                            overflow = TextOverflow.Clip,
                            color = Color.Black,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(6.dp),
                            text = product.category,
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            text = "$ ${product.price}",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = androidx.compose.ui.text.style.TextAlign.End,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}







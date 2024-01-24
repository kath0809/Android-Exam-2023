package no.pgr208.knr2044.screens.store_details

import ProductDetailsViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import no.pgr208.knr2044.R
import no.pgr208.knr2044.ui.theme.CallToAction


@Composable
fun ProductDetailsScreen(
    viewModel: ProductDetailsViewModel,
    onBackButtonClick: () -> Unit = {},
    navigateToCart: () -> Unit = {}
) {
    val productState = viewModel.selectedProduct.collectAsState()
    val isLoading = viewModel.loading.collectAsState()
    val isFavorite = viewModel.isFavorite.collectAsState()

    if(isLoading.value) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    val product = productState.value

    if(product == null) {
        Text(text = "Failed to get product details. Selected product object is null!")
        return
    }


    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp)
                .background(Color.White),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { onBackButtonClick() }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Refresh astronauts",
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(65.dp)
                    .background(Color.White),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(onClick = { navigateToCart() }
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(8.dp),
                        imageVector = Icons.Outlined.ShoppingCart,
                        contentDescription = "Refresh astronauts",
                        )
                }

                IconButton(onClick = {viewModel.updateFavorite(product.id)}
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(8.dp),
                        imageVector = if (isFavorite.value) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Add product to favorites",
                        tint = Color.Red,
                    )
                }
            }
        }

        Divider()
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(state = rememberScrollState())
        ) {
            Text(
                text = product.title,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Black,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                )

            Spacer(modifier = Modifier.height(16.dp))
            AsyncImage(
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.CenterHorizontally),
                model = product.image,
                alignment = Alignment.Center,
                contentScale = ContentScale.Fit,
                contentDescription = "Image of ${product.title}",
                placeholder = painterResource(id = R.drawable.store_placeholder)
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier
                    .padding(15.dp)
                    .align(Alignment.CenterHorizontally),
                text = product.description,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Normal,
                color = Color.Black,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Button(onClick = { viewModel.updateCart(product.id) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(CallToAction)
                ) {
                    Text(text = "Add to cart ${product.price}")
                }
            }
        }
    }
}


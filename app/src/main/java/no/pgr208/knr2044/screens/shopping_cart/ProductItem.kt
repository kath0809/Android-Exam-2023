package no.pgr208.knr2044.screens.shopping_cart


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import no.pgr208.knr2044.product_data.Product
import no.pgr208.knr2044.ui.theme.CallToAction


@Composable
fun ProductItem(
    product: Product,
    removeOnClick: () -> Unit,
    increaseOnClick: () -> Unit,
    decreaseOnClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
        ){
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(70.dp, 70.dp)
                    .padding(end = 8.dp)
                    .clip(RoundedCornerShape(8.dp)),
                model = product.image,
                contentDescription = "Image of ${product.title}",
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1f),
            ) {
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Clip,
                )
                Text(
                    modifier = Modifier
                        .padding(top = 4.dp),
                    text = "price per item $ ${product.price}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black,
                )

                Spacer(modifier = Modifier.size(8.dp))

                Row(
                       modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Absolute.Left
                ) {
                    Button(
                        onClick = { increaseOnClick() },
                        modifier = Modifier
                            .size(70.dp, 30.dp)
                            .clip(RoundedCornerShape(50)),
                        colors = ButtonDefaults.buttonColors(CallToAction.copy(alpha = 0.5f))
                    ) {
                        Text(
                            modifier = Modifier,
                            text = "+",
                            color = Color.Black,
                            style = MaterialTheme.typography.labelLarge,
                            )
                    }
                    Spacer(modifier = Modifier.size(8.dp))
                    Button(onClick = { decreaseOnClick() },
                        modifier = Modifier
                            .size(70.dp, 30.dp)
                            .clip(RoundedCornerShape(50)),
                        colors = ButtonDefaults.buttonColors(CallToAction.copy(alpha = 0.5f))
                    ) {
                        Text(
                            text = "-",
                            color = Color.Black,
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.size(8.dp))
                    Button(onClick = { removeOnClick() },
                        modifier = Modifier
                            .size(70.dp , 30.dp)
                            .clip(RoundedCornerShape(50)),
                        colors = ButtonDefaults.buttonColors(CallToAction.copy(alpha = 0.5f)),
                    ) {
                        Text(
                            text = "x",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.Black,
                        )
                    }
                }
            }
        }
    }
}
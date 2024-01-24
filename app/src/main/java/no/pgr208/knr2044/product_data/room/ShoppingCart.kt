package no.pgr208.knr2044.product_data.room


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ShoppingCart")
data class ShoppingCart(
    @PrimaryKey
    val productId: Int,
    val quantity: Int,
    val productTitle: String,
)
package no.pgr208.knr2044.product_data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @PrimaryKey
    val id: Int,
    val title: String,
    val price: Float,
    val category: String,
    val description: String,
    val image: String?
)


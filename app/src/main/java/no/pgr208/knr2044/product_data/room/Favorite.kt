package no.pgr208.knr2044.product_data.room

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "ProductFavorite")
data class Favorite(
    @PrimaryKey
    val productId: Int
)
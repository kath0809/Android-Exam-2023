package no.pgr208.knr2044.product_data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity(tableName = "OrderHistory")
data class OrderHistory(
    @PrimaryKey(autoGenerate = true)
    val orderId: Int,
    val items: String,
    val totalPrice: Int,
    val date: Date
)
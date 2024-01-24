package no.pgr208.knr2044.product_data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HistoryDao {
    @Query("SELECT * FROM OrderHistory")
    suspend fun getHistory(): List<OrderHistory>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToHistory(orderHistory: OrderHistory)
}
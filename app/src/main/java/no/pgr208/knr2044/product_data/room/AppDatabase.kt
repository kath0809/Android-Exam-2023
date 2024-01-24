

package no.pgr208.knr2044.product_data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import no.pgr208.knr2044.product_data.Product


@Database(
    entities = [Product::class, ShoppingCart::class, OrderHistory::class, Favorite::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(TimeConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getProductDao(): ProductDao
    abstract fun cartDao(): CartDao
    abstract fun historyDao(): HistoryDao
    abstract fun favoriteDao(): FavoriteDao
}

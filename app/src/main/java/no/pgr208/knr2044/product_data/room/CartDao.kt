package no.pgr208.knr2044.product_data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CartDao {
    @Query("SELECT * FROM ShoppingCart")
    suspend fun getCart(): List<ShoppingCart>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToCart(shoppingCart: ShoppingCart)

    @Delete
    suspend fun removeFromCart(shoppingCart: ShoppingCart)

    @Query("UPDATE ShoppingCart SET quantity = :quantity WHERE productId = :productId")
    suspend fun updateQuantity(productId: Int, quantity: Int)
}
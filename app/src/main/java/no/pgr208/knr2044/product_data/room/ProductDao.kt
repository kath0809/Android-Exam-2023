
package no.pgr208.knr2044.product_data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import no.pgr208.knr2044.product_data.Product

@Dao
interface ProductDao {

    @Query("SELECT * FROM Product")
    suspend fun getAllProducts(): List<Product>

    @Query("SELECT * FROM Product WHERE :productId = id")
    suspend fun getProductById(productId: Int): Product?

    @Query("SELECT * FROM Product WHERE id IN (:idList)")
    suspend fun getProductsByIds(idList: List<Int>): List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProduct(product: List<Product>)





}



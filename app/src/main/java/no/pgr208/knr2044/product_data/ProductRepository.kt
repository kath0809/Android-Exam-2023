package no.pgr208.knr2044.product_data

import android.content.Context
import android.util.Log
import androidx.room.Room
import no.pgr208.knr2044.product_data.room.AppDatabase
import no.pgr208.knr2044.product_data.room.Favorite
import no.pgr208.knr2044.product_data.room.OrderHistory
import no.pgr208.knr2044.product_data.room.ShoppingCart
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ProductRepository {

    private val _httpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .build()

    private val _retrofit =
        Retrofit.Builder()
            .client(_httpClient)
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val _productService = _retrofit.create(ProductService::class.java)


    // Database
    private lateinit var _appDatabase: AppDatabase
    private val _cartDao by lazy { _appDatabase.cartDao() }
    private val _productDao by lazy { _appDatabase.getProductDao() }
    private val _historyDao by lazy { _appDatabase.historyDao() }
    private val _favoriteDao by lazy { _appDatabase.favoriteDao() }

    fun initializeAppDatabase(context: Context) {
        _appDatabase = Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "product_database"
        ).fallbackToDestructiveMigration().build()
    }


    suspend fun getAllProducts(): List<Product> {
        try {
            val response = _productService.getAllProducts()

            if(response.isSuccessful) {

                val products = response.body() ?: emptyList()

                _productDao.addProduct(products)

                return _productDao.getAllProducts()
            } else {
                throw Exception("Response was not successful")
            }
        } catch (e: Exception) {
            Log.v("ProductRepository", "Failed to get products from API", e)
            return _productDao.getAllProducts()
        }
    }


    suspend fun getProductById(productId: Int): Product? {
        return _productDao.getProductById(productId)
    }

    suspend fun getProductsByIds(idList: List<Int>): List<Product> {
        return _productDao.getProductsByIds(idList)
    }


    // Handlevogn
    suspend fun getCart(): List<ShoppingCart> {
        return _cartDao.getCart()
    }

    suspend fun addToCart(shoppingCart: ShoppingCart) {
        _cartDao.addToCart(shoppingCart)
    }

    suspend fun removeFromCart(shoppingCart: ShoppingCart) {
        _cartDao.removeFromCart(shoppingCart)
    }

    suspend fun updateQuantity(productId: Int, quantity: Int) {
        _cartDao.updateQuantity(productId, quantity)
    }


    // Ordre historikk
    suspend fun getHistory(): List<OrderHistory> {
        return _historyDao.getHistory()
    }

    suspend fun addToOrderHistory(orderHistory: OrderHistory) {
        _historyDao.addToHistory(orderHistory)
    }


    // Legge til og fjerne favoritt produkter
    suspend fun getFavorites(): List<Favorite> {
        return _favoriteDao.getFavorites()
    }

    suspend fun addFavorite(favorite: Favorite) {
        _favoriteDao.insertFavorite(favorite)
    }

    suspend fun removeFavorite(favorite: Favorite) {
        _favoriteDao.removeFavorite(favorite)
    }

}
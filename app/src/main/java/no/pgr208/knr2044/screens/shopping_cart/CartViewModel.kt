package no.pgr208.knr2044.screens.shopping_cart

import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import no.pgr208.knr2044.product_data.Product
import no.pgr208.knr2044.product_data.ProductRepository
import no.pgr208.knr2044.product_data.room.CartDao
import no.pgr208.knr2044.product_data.room.OrderHistory
import no.pgr208.knr2044.product_data.room.ShoppingCart
import java.time.LocalDateTime
import java.util.Date

class CartViewModel : ViewModel() {
    private val _inShoppingCart = MutableStateFlow<List<Product>>(emptyList())
    val inShoppingCart = _inShoppingCart.asStateFlow()
    val totalPrice = mutableIntStateOf(0)



    fun loadShoppingCart() {
        viewModelScope.launch(Dispatchers.IO) {
            val listOfCartItems = ProductRepository.getCart().map { it.productId }
            _inShoppingCart.value = ProductRepository.getProductsByIds(listOfCartItems)
            updateTotalPrice()
        }
    }

    // Etter at man trykker på place order tømmes handlekurven og totalprisen.
    fun clearCart() {
        viewModelScope.launch(Dispatchers.IO) {
            val cartItems = ProductRepository.getCart()
            cartItems.forEach{ cartItem ->
                ProductRepository.removeFromCart(cartItem)
            }
            loadShoppingCart()
            updateTotalPrice()
        }
    }


    // Oppdaterer total prisen i kurven ettersom produkter legges til/fjernes.
    suspend fun updateTotalPrice() {
        var total = 0.0
        val cartItems = ProductRepository.getCart()
        for (product in _inShoppingCart.value) {
            val quantity = cartItems.find { it.productId == product.id }?.quantity ?: 0
            total += product.price * quantity
        }
        totalPrice.intValue = total.toInt()
    }

    // Sletter alle av ett produkt
    fun removeProductFromCart(productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val shoppingCart = ShoppingCart(productId, 0, "")
            ProductRepository.removeFromCart(shoppingCart)
            loadShoppingCart()
            updateTotalPrice()
        }
    }

    // Øker antallet av ett produkt med 1
    fun increaseQuantity(productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val shoppingCart = ProductRepository.getCart().find { it.productId == productId }
            if (shoppingCart != null) {
                val newQuantity = shoppingCart.quantity + 1
                ProductRepository.updateQuantity(productId, newQuantity)
                loadShoppingCart()
                updateTotalPrice()
            }
        }
    }

    // Reduserer antallet av ett produkt med 1
    fun decreaseQuantity(productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val shoppingCart = ProductRepository.getCart().find { it.productId == productId }
            if (shoppingCart != null && shoppingCart.quantity > 1) {
                val newQuantity = shoppingCart.quantity - 1
                ProductRepository.updateQuantity(productId, newQuantity)
                loadShoppingCart()
                updateTotalPrice()
            }
        }
    }

    // Legger produkter fra handlekurven til ordre historikken
    fun addToOrderHistory() {
        viewModelScope.launch{
            val cartItems = ProductRepository.getCart()
            val items = Gson().toJson(cartItems)
                val orderHistory = OrderHistory(
                    orderId = 0,
                    items = items,
                    totalPrice = totalPrice.intValue,
                    date = Date()
                )
                ProductRepository.addToOrderHistory(orderHistory)
            }
            clearCart()
        }
    }

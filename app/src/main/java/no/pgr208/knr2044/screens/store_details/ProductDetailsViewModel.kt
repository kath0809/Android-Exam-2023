
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import no.pgr208.knr2044.product_data.Product
import no.pgr208.knr2044.product_data.ProductRepository
import no.pgr208.knr2044.product_data.room.Favorite
import no.pgr208.knr2044.product_data.room.ShoppingCart


class ProductDetailsViewModel : ViewModel() {
    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct = _selectedProduct.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _isInCart = MutableStateFlow(false)
    val isInCart = _isInCart.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite = _isFavorite.asStateFlow()

    fun setSelectedProduct(productId: Int) {
        viewModelScope.launch {
            _loading.value = true
            _selectedProduct.value = ProductRepository.getProductById(productId)
            _isInCart.value = isCurrentProductInCart()
            _isFavorite.value = isProductFavorite()
            _loading.value = false
        }
    }


    fun updateCart(productId: Int) {
        viewModelScope.launch {
            ProductRepository.addToCart(
                ShoppingCart(
                    productId,
                    1,
                    selectedProduct.value?.title ?: ""
                )
            )
            _isInCart.value = isCurrentProductInCart()
        }
    }

    private suspend fun isCurrentProductInCart(): Boolean {
        return ProductRepository.getCart().any { it.productId == selectedProduct.value?.id }
    }

    //
    fun updateFavorite(productId: Int) {
        viewModelScope.launch {
            if (isFavorite.value) {
                ProductRepository.removeFavorite(Favorite(productId))
            } else {
                ProductRepository.addFavorite(Favorite(productId))
            }
            _isFavorite.value = isProductFavorite()
        }
    }

    private suspend fun isProductFavorite(): Boolean {
        return ProductRepository.getFavorites().any { it.productId == selectedProduct.value?.id }
    }
}



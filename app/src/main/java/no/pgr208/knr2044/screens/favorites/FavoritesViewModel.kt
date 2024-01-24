package no.pgr208.knr2044.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import no.pgr208.knr2044.product_data.Product
import no.pgr208.knr2044.product_data.ProductRepository

class FavoritesViewModel : ViewModel() {

    private val _favoriteProducts = MutableStateFlow<List<Product>>(emptyList())
    val favoriteProducts = _favoriteProducts.asStateFlow()

    fun loadFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            val listOfFavoriteIds = ProductRepository.getFavorites().map { it.productId }
            _favoriteProducts.value = ProductRepository.getProductsByIds(listOfFavoriteIds)
        }
    }
}
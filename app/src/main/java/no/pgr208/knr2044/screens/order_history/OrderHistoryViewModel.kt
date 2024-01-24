package no.pgr208.knr2044.screens.order_history

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import no.pgr208.knr2044.product_data.ProductRepository
import no.pgr208.knr2044.product_data.room.OrderHistory

class OrderHistoryViewModel: ViewModel() {
    val orderHistory = mutableStateOf<List<OrderHistory>>(emptyList())

    init {
        loadOrderHistory()
    }

    fun loadOrderHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            orderHistory.value = ProductRepository.getHistory()
            val products = ProductRepository.getAllProducts()
            val productTitleMap = products.associateBy({ it.id }, { it.title })
        }
    }
}
package no.pgr208.knr2044

import OrderHistoryScreen
import no.pgr208.knr2044.screens.store_details.ProductDetailsScreen
import ProductDetailsViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import no.pgr208.knr2044.product_data.ProductRepository
import no.pgr208.knr2044.screens.favorites.FavoritesScreen
import no.pgr208.knr2044.screens.favorites.FavoritesViewModel
import no.pgr208.knr2044.screens.order_history.OrderHistoryViewModel
import no.pgr208.knr2044.screens.shopping_cart.CartScreen
import no.pgr208.knr2044.screens.shopping_cart.CartViewModel
import no.pgr208.knr2044.screens.store_list.StoreListScreen
import no.pgr208.knr2044.screens.store_list.StoreListViewModel
import no.pgr208.knr2044.ui.theme.Pgr208Exam2023Theme

class MainActivity : ComponentActivity() {
    private val _storeListViewModel: StoreListViewModel by viewModels()
    private val _productDetailsViewModel: ProductDetailsViewModel by viewModels()
    private val _cartViewModel: CartViewModel by viewModels()
    private val _orderHistoryViewModel: OrderHistoryViewModel by viewModels()
    private val _favoritesViewModel: FavoritesViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ProductRepository.initializeAppDatabase(applicationContext)

        setContent {
            Pgr208Exam2023Theme {
                val navController = rememberNavController()

                // NavHost is used to navigate between screens
                NavHost(
                    navController = navController,
                    startDestination = "storeListScreen"
                ) {
                    composable(
                        route = "storeListScreen"
                    ) {
                            StoreListScreen(
                                viewModel = _storeListViewModel,
                                onProductClick = { productId ->
                                    navController.navigate("productDetailsScreen/$productId")
                                },
                                navigateToCart = {
                                    navController.navigate("cartScreen")
                                },
                                navigateToHistory = {
                                    navController.navigate("orderHistoryScreen")
                                },
                                navigateToFavorites = {
                                    navController.navigate("favoritesScreen")
                                }
                            )
                    }

                    composable(
                        route = "productDetailsScreen/{productId}",
                        arguments = listOf(
                            navArgument(name = "productId") {
                                type = NavType.IntType
                            }
                    )
                    ) { backStackEntry ->
                        val productId = backStackEntry.arguments?.getInt("productId") ?: -1
                        LaunchedEffect(productId) {
                            _productDetailsViewModel.setSelectedProduct(productId)
                        }

                        ProductDetailsScreen(
                            viewModel = _productDetailsViewModel,
                            onBackButtonClick = {
                                navController.popBackStack()
                            },
                            navigateToCart = {
                                navController.navigate("cartScreen")
                            }
                        )
                    }

                    composable(route = "cartScreen") {
                        LaunchedEffect(Unit) {
                            _cartViewModel.loadShoppingCart()
                        }

                        CartScreen(
                            viewModel = _cartViewModel,
                            onBackButtonClick = {navController.popBackStack() },
                            onProductClicked = { productId ->
                                navController.navigate("productDetailsScreen/$productId")
                            },
                            navController = navController
                        )
                    }
                    composable(route = "orderHistoryScreen") {
                        LaunchedEffect(Unit) {
                            _orderHistoryViewModel.loadOrderHistory()
                        }

                        OrderHistoryScreen(
                            viewModel = _orderHistoryViewModel,
                            onBackButtonClick = {navController.popBackStack() },
                        )
                    }
                    composable(route = "favoritesScreen") {
                        LaunchedEffect(Unit) {
                            _favoritesViewModel.loadFavorites()
                        }
                        FavoritesScreen(
                            viewModel = _favoritesViewModel,
                            onBackButtonClick = {navController.popBackStack() },
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}


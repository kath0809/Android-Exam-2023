package no.pgr208.knr2044.product_data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductService {

    @GET("products")
    suspend fun getAllProducts(): Response<List<Product>>

    // Hente ut all informasjon om produkter fra API'et
    @GET("products/{productId}")
    suspend fun getProductById(
        @Path("productId") id: Int
    ): Response<Product>
}



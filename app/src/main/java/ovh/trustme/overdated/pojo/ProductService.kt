package ovh.trustme.overdated.pojo

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface ProductService {
    @GET("/api/v0/product/{ean13}.json")
    fun productGot(@Path("ean13") ean13: String?): Call<RequeteOpenfood>
}
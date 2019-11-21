package ovh.trustme.overdated.pojo

import retrofit2.Call
import retrofit2.http.GET


interface ProductService {
    @GET("/api/v0/product/737628064502.json")
    fun productGot(): Call<RequeteOpenfood>
}
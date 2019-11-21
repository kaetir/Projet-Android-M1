package ovh.trustme.overdated.pojo

import ovh.trustme.overdated.pojo.Requette_openfood
import retrofit2.Call
import retrofit2.http.GET


interface ProductService {
    @GET("/737628064502.json")
    fun productGot(): Call<Requette_openfood>
}
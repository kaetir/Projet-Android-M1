package ovh.trustme.overdated.pojo

import com.google.gson.annotations.SerializedName

class Requette_openfood(
    @SerializedName("code")
    var code: String,
    @SerializedName("status")
    var status: Int,
    @SerializedName("product")
    var product: Product_openfood
) {

    override fun toString(): String {
        return "Requette_openfood(code='$code', status=$status, product$product)"
    }
}

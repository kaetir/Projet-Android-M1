package ovh.trustme.overdated.pojo

import com.google.gson.annotations.SerializedName

class RequeteOpenfood(
    @SerializedName("code")
    var code: String,
    @SerializedName("status")
    var status: Int,
    @SerializedName("product")
    var product: ProductOpenfood
) {

    override fun toString(): String {
        return "Requete_openfood(code='$code', status=$status, product$product)"
    }
}

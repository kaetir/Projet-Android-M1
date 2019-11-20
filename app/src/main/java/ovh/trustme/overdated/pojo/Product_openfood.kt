package ovh.trustme.overdated.pojo

import com.google.gson.annotations.SerializedName

class Product_openfood(
    @SerializedName("image_url")
    val image_url: String,
    @SerializedName("traces_from_ingredients")
    val traces_from_ingredients: List<String>,
    @SerializedName("product_name_fr")
    val product_name: String
) {
    override fun toString(): String {
        return "Product_openfood(image_url='$image_url', traces_from_ingredients=$traces_from_ingredients, product_name='$product_name')"
    }
}
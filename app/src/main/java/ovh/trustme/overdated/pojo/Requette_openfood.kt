package ovh.trustme.overdated.pojo

class Requette_openfood(
    var code: String,
    var status: Int,
    var product: Product_openfood
) {

    override fun toString(): String {
        return "Requette_openfood(code='$code', status=$status, product$product)"
    }
}

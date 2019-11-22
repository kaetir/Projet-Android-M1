package ovh.trustme.overdated.adapters

import android.content.Context
import android.net.Uri
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ovh.trustme.overdated.R
import ovh.trustme.overdated.database.Product


class ProductAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var products = emptyList<Product>() // Cached copy of products

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productNameItemView: TextView = itemView.findViewById(R.id.list_product_element_name)
        val productDateItemView: EditText = itemView.findViewById(R.id.list_product_element_date)
        val productImageItemView: ImageView = itemView.findViewById(R.id.list_product_element_picture)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = inflater.inflate(R.layout.product_list_element, parent, false)
        return ProductViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val current = products[position]
        holder.productNameItemView.text = current.name
        holder.productDateItemView.text = Editable.Factory.getInstance().newEditable(current.date)
        Picasso.get().load(Uri.parse(current.urlImage)).into(holder.productImageItemView)
    }

    internal fun setProducts(products: List<Product>) {
        this.products = products
        notifyDataSetChanged()
    }

    override fun getItemCount() = products.size
}
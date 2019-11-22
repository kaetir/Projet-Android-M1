package ovh.trustme.overdated.ui.list_products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ovh.trustme.overdated.ProductsViewModel
import ovh.trustme.overdated.R
import ovh.trustme.overdated.adapters.ProductAdapter

class ListProductFragment : Fragment() {

    private lateinit var productViewModel: ProductsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_list_products, container, false)

        val recyclerView = root.findViewById<RecyclerView>(R.id.liste_produits_recycler_view)
        val adapter = this!!.context?.let { ProductAdapter(it) }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Get a new or existing ViewModel from the ViewModelProvider.
        productViewModel = ViewModelProviders.of(activity!!).get(ProductsViewModel::class.java)

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        productViewModel.products.observe(this, Observer { words ->
            // Update the cached copy of the words in the adapter.
            words?.let { adapter?.setProducts(it) }
        })

        return root
    }
}
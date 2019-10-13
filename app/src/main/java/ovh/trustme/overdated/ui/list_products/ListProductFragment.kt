package ovh.trustme.overdated.ui.list_products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import ovh.trustme.overdated.R

class ListProductFragment : Fragment() {

    private lateinit var listProductsViewModel: ListProductsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listProductsViewModel =
                ViewModelProviders.of(this).get(listProductsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_listProducts, container, false)
        val textView: TextView = root.findViewById(R.id.text_slideshow)
        listProductsViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}
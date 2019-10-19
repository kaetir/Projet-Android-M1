package ovh.trustme.overdated.ui.list_products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import ovh.trustme.overdated.R

class ListProductFragment : Fragment() {

    private lateinit var listProductsViewModel: ListProductsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listProductsViewModel = ListProductsViewModel()

        //        ViewModelProviders.of(this).get(listProductsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_list_products, container, false)
        /*val textView: TextView = root.findViewById(R.id.text_list_products)
        listProductsViewModel.text.observe(this, Observer {
            textView.text = it
        })*/
        return root
    }
}
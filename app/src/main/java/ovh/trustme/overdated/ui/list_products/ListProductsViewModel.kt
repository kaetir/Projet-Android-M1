package ovh.trustme.overdated.ui.list_products


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ListProductsViewModel : ViewModel() {


    private val _text = MutableLiveData<String>().apply {
        value = "Liste des produits"
    }
    val text: LiveData<String> = _text

    
}
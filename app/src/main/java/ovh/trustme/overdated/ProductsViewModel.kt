package ovh.trustme.overdated

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ovh.trustme.overdated.database.Product
import ovh.trustme.overdated.database.ProductDatabase
import ovh.trustme.overdated.database.ProductRepository

// ViewModel shared between camera and list_products fragments :

class ProductsViewModel(application: Application) : AndroidViewModel(application) {

    // The ViewModel maintains a reference to the repository to get data.
    private val repository: ProductRepository
    // LiveData gives us updated products when they change.
    val products: LiveData<List<Product>>

    init {
        // Gets reference to ProductDao from ProductRoomDatabase to construct
        // the correct ProductRepository.
        val productsDao = ProductDatabase.getDatabase(application, viewModelScope).productDao()
        repository = ProductRepository(productsDao)
        products = repository.products
    }

    /**
     * The implementation of insert() in the database is completely hidden from the UI.
     * Room ensures that you're not doing any long running operations on
     * the main thread, blocking the UI, so we don't need to handle changing Dispatchers.
     * ViewModels have a coroutine scope based on their lifecycle called
     * viewModelScope which we can use here.
     */
    fun insert(product: Product) = viewModelScope.launch {
        repository.insert(product)
    }
}
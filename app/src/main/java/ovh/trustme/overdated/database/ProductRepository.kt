package ovh.trustme.overdated.database

import androidx.lifecycle.LiveData

class ProductRepository(private val productDao: ProductDao) {
        // Room executes all queries on a separate thread.
        // Observed LiveData will notify the observer when the data has changed.
        val products: LiveData<List<Product>> = productDao.getAll()

        suspend fun insert(product: Product) {
            productDao.insert(product)
        }
}
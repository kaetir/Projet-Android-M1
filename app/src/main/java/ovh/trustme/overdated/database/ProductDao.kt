package ovh.trustme.overdated.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProductDao{

    // LiveData is a data holder class that can be observed within a given lifecycle.
    // Always holds/caches latest version of data. Notifies its active observers when the
    // data has changed. Since we are getting all the contents of the database,
    // we are notified whenever any of the database contents have changed.
    @Query("SELECT * FROM products_database ORDER BY date ASC")
    fun getAll():LiveData<List<Product>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg product: Product)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(product: Product)

    @Delete
    suspend fun delete(product: Product)

    @Query("DELETE FROM products_database")
    suspend fun deleteAll()

    @Update
    fun updateProduct(vararg product: Product)
}
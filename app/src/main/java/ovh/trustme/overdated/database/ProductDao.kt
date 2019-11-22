package ovh.trustme.overdated.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProductDao{
    @Query("SELECT * FROM Products")
    fun getAll():LiveData<List<Product>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg product: Product)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(product: Product)

    @Delete
    suspend fun delete(product: Product)

    @Query("DELETE FROM Products")
    suspend fun deleteAll()

    @Update
    fun updateProduct(vararg product: Product)
}
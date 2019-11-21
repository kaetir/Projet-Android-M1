package ovh.trustme.overdated.database

import androidx.room.*

@Dao
interface ProductDao{
    @Query("Select * from Products")
    fun getAll():List<Product>

    @Insert()
    fun insertAll(vararg product: Product)

    @Delete
    fun delete(product: Product)

    @Update
    fun updateProduct(vararg product: Product)
}
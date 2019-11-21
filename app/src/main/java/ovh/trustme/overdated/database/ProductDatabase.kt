package ovh.trustme.overdated.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.security.AccessControlContext

@Database(entities = arrayOf(Product::class), version = 1)
abstract class ProductDatabase: RoomDatabase(){
    abstract fun productDao():ProductDao

    companion object {
        @Volatile private var instance: ProductDatabase? = null
        private val LOCK= Any()

        operator fun invoke(context: Context)= instance?: synchronized(LOCK){
            instance?: buildDatabase(context).also{ instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context, ProductDatabase::class.java, "Products.db").build()

    }
}
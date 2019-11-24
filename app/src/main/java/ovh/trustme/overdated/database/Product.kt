package ovh.trustme.overdated.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName="products_database")
data class Product (

    @PrimaryKey(autoGenerate = true)
    var id: Int,

    var date: String,

    var name: String,

    var dlc_dluo: String,

    var urlImage: String

)

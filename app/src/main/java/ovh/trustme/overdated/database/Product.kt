package ovh.trustme.overdated.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName="Products")
data class Product (

    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo
    var date: String,

    @ColumnInfo
    var name: String,

    @ColumnInfo
    var dlc_dluo: String,

    @ColumnInfo
    var urlImage: String

)

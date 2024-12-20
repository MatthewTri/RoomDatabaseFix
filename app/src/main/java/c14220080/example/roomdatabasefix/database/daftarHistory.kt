package c14220080.example.roomdatabasefix.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class daftarHistory(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    var id: Int = 0,

    @ColumnInfo(name = "tanggal")
    var tanggal: String? = null,

    @ColumnInfo(name = "item")
    var item: String? = null,

    @ColumnInfo(name = "jumlah")
    var jumlah: String? = null,

    @ColumnInfo(name = "status")
    var status: Int = 0,
)

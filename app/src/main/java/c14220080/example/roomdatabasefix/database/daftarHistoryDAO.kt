package c14220080.example.roomdatabasefix.database

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Dao

@Dao
interface daftarHistoryDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(daftar: daftarHistory)


    @Query("SELECT * FROM daftarHistory ORDER BY id ASC")
    fun selectAll(): MutableList<daftarHistory>

    @Query("SELECT * FROM daftarHistory WHERE id = :isi_id")
    suspend fun getItem(isi_id : Int) : daftarHistory
}
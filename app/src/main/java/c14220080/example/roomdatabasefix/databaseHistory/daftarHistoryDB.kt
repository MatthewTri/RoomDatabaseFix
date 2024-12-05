package c14220080.example.roomdatabasefix.databaseHistory

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [daftarHistory::class], version =1)
abstract class daftarHistoryDB : RoomDatabase() {
    abstract fun fundaftarHistoryDAO(): daftarHistoryDAO

    companion object {
        @Volatile
        private var INSTANCE: daftarHistoryDB? = null

        @JvmStatic
        fun getDatabase(context: Context) : daftarHistoryDB {
            if (INSTANCE == null) {
                synchronized(daftarHistoryDB::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        daftarHistoryDB::class.java,
                        "daftar_history"
                    ).allowMainThreadQueries().build()
                }
            }
            return INSTANCE as daftarHistoryDB
        }
    }
}
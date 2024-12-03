package c14220080.example.roomdatabasefix

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import c14220080.example.roomdatabasefix.database.daftarBelanja
import c14220080.example.roomdatabasefix.database.daftarBelanjaDB
import c14220080.example.roomdatabasefix.helper.DateHelper.getCurrentDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class TambahDaftar : AppCompatActivity() {

    var DB = daftarBelanjaDB.getDatabase(this)
    var iID : Int = 0
    var iAddEdit : Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tambah_daftar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var tanggal = getCurrentDate()
        var item = findViewById<EditText>(R.id.etItem)
        var jumlah = findViewById<EditText>(R.id.etJumlah)
        var btnTambah = findViewById<Button>(R.id.btnTambah)
        var btnUpdate = findViewById<Button>(R.id.btnUpdate)

        btnTambah.setOnClickListener {
            CoroutineScope(Dispatchers.IO).async {
                DB.fundaftarBelanjaDAO().insert(
                    daftarBelanja(
                        tanggal = tanggal,
                        item = item.text.toString(),
                        jumlah = jumlah.text.toString())
                )
            }
            finish()
        }

        iID = intent.getIntExtra("id", 0)
        iAddEdit = intent.getIntExtra("addEdit", 0)

        if (iAddEdit == 0) {
            btnTambah.visibility = View.VISIBLE
            btnUpdate.visibility = View.GONE
            item.isEnabled = true
        } else {
            btnTambah.visibility = View.GONE
            btnUpdate.visibility = View.VISIBLE
            item.isEnabled = false

            CoroutineScope(Dispatchers.IO).async {
                var item1 = DB.fundaftarBelanjaDAO().getItem(iID)
                    item.setText(item1.item)
                    jumlah.setText(item1.jumlah)
            }
        }

        btnUpdate.setOnClickListener {
            CoroutineScope(Dispatchers.IO).async {
                DB.fundaftarBelanjaDAO().update(
                    isi_tanggal = tanggal,
                    isi_item = item.text.toString(),
                    isi_jumlah = jumlah.text.toString(),
                    isi_status = 0,
                    pilihid = iID
                )
            }
            finish()
        }

    }
}
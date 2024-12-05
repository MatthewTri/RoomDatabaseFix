package c14220080.example.roomdatabasefix

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import c14220080.example.roomdatabasefix.database.daftarBelanja
import c14220080.example.roomdatabasefix.databaseHistory.daftarHistory
import c14220080.example.roomdatabasefix.databaseHistory.daftarHistoryDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class adapterDaftar (private val daftarBelanja : MutableList<daftarBelanja>):
    RecyclerView.Adapter<adapterDaftar.ListViewHolder>() {

        private lateinit var onItemClickCallback : OnItemClickCallback

        interface OnItemClickCallback {
            fun delData(dtBelanja: daftarBelanja)
        }
        fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
            this.onItemClickCallback = onItemClickCallback
        }

    class ListViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _tvTanggal = itemView.findViewById<TextView>(R.id.tvItem)
        var _tvItemBarang = itemView.findViewById<TextView>(R.id.item)
        var _tvJumlahBarang = itemView.findViewById<TextView>(R.id.jumlah)

        var _btnEdit= itemView.findViewById<TextView>(R.id.edit)
        var _btnDelete= itemView.findViewById<TextView>(R.id.delete)
        var _btnSelesai = itemView.findViewById<Button>(R.id.selesai)
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): adapterDaftar.ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: adapterDaftar.ListViewHolder, position: Int) {
        var daftar = daftarBelanja[position]

        holder._tvTanggal.setText(daftar.tanggal)
        holder._tvItemBarang.setText(daftar.item)
        holder._tvJumlahBarang.setText(daftar.jumlah)

        holder._btnEdit.setOnClickListener {
            val intent = Intent(it.context, TambahDaftar::class.java)
            intent.putExtra("id", daftar.id)
            intent.putExtra("addEdit", 1)
            it.context.startActivity(intent)
        }


        holder._btnDelete.setOnClickListener {
            onItemClickCallback.delData(daftar)
        }

        holder._btnSelesai.setOnClickListener {
            CoroutineScope(Dispatchers.IO).async {
                // Insert the data into databaseHistory
                val historyDB = daftarHistoryDB.getDatabase(it.context)
                historyDB.fundaftarHistoryDAO().insert(
                    daftarHistory(
                        tanggal = daftar.tanggal,
                        item = daftar.item,
                        jumlah = daftar.jumlah
                    )
                )
                // Delete the data from the current database
                onItemClickCallback.delData(daftar)
            }
        }

    }



    fun isiData(daftar: List<daftarBelanja>) {
        daftarBelanja.clear()
        daftarBelanja.addAll(daftar)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return daftarBelanja.size
    }
}
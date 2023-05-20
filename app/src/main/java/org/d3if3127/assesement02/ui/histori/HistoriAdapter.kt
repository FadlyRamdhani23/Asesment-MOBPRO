package org.d3if3127.assesement02.ui.histori

import android.annotation.SuppressLint
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.d3if3127.assesement02.db.DataEntity
import org.d3if3127.assesement02.model.KategoriBmi
import org.d3if3127.assesement02.model.hitungBmr
import org.d3if3127.assesement02.model.hitungData
import org.d3if3127.assesment02.R
import org.d3if3127.assesment02.databinding.ItemHistoriBinding
import java.text.SimpleDateFormat
import java.util.*


class HistoriAdapter :
    ListAdapter<DataEntity, HistoriAdapter.ViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<DataEntity>() {
                override fun areItemsTheSame(
                    oldData: DataEntity, newData: DataEntity
                ): Boolean {
                    return oldData.id == newData.id
                }
                override fun areContentsTheSame(
                    oldData: DataEntity, newData: DataEntity
                ): Boolean {
                    return oldData == newData
                }
            }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHistoriBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    class ViewHolder(
        private val binding: ItemHistoriBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val dateFormatter = SimpleDateFormat("dd MMMM yyyy",
            Locale("id", "ID")
        )
        @SuppressLint("StringFormatMatches")
        fun bind(item: DataEntity) = with(binding) {
            val hasilBmi = item.hitungData()
            val hasilBmr = item.hitungBmr()
            kategoriTextView.text = hasilBmi.kategori.toString().substring(0, 1)
            val colorRes = when(hasilBmi.kategori) {
                KategoriBmi.KURUS -> R.color.kurus
                KategoriBmi.IDEAL -> R.color.ideal
                else -> R.color.gemuk
            }
            val circleBg = kategoriTextView.background as GradientDrawable
            circleBg.setColor(ContextCompat.getColor(root.context, colorRes))
            tanggalTextView.text = dateFormatter.format(Date(item.tanggal))

            if(item.pilihan.equals("BMR")){
                bmiTextView.text = root.context.getString(R.string.hasil_x1,
                    hasilBmr.perbedaan, hasilBmr.hasilProtein)
            }else{
                bmiTextView.text = root.context.getString(R.string.hasil_x,
                    hasilBmi.bmi, hasilBmi.kategori.toString())
            }



            val gender = root.context.getString(
                if (item.isMale) R.string.pria else R.string.wanita)
            dataTextView.text = root.context.getString(R.string.data_x,
                item.berat, item.tinggi, gender)
        }
    }
}

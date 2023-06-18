package org.d3if3127.assesement02.ui.food

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.d3if3127.assesement02.model.Food
import org.d3if3127.assesement02.network.MakananApi
import org.d3if3127.assesment02.R
import org.d3if3127.assesment02.databinding.ListFoodBinding

class FoodAdapter : RecyclerView.Adapter<FoodAdapter.ViewHolder>() {
    private val data = mutableListOf<Food>()

    fun updateData(newData: List<Food>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }
    class ViewHolder(
        private val binding: ListFoodBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(Food: Food) = with(binding) {
            namaFoodTextView.text = Food.nama
            jumlahCaloriesTextView.text = Food.kalori.toString()
            jumlahProteinTextView.text = Food.Protein.toString()
            Glide.with(imageView.context)
                .load(MakananApi.getMakananUrl(Food.imageResId))
                .error(R.drawable.baseline_broken_image_24)
                .into(imageView)
            root.setOnClickListener {
                val message = root.context.getString(R.string.message, Food.nama)
                Toast.makeText(root.context, message, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListFoodBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }
}
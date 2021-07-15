package com.example.portal.uikit


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.portal.databinding.RowYearSelectBinding

class YearSelectAdapter(private val onClick: (Int) -> Unit) :
    RecyclerView.Adapter<YearSelectViewHolder>() {

    private val itemList = mutableListOf<Int>()

    fun updateData(itemList: List<Int>) {
        this.itemList.clear()
        this.itemList.addAll(itemList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YearSelectViewHolder {
        return YearSelectViewHolder(
            RowYearSelectBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = itemList.size

    override fun onBindViewHolder(holder: YearSelectViewHolder, position: Int) {
        holder.bind(itemList[position], onClick)
    }
}

class YearSelectViewHolder(private val binding: RowYearSelectBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Int, onClick: (Int) -> Unit) {
        binding.textView.apply {
            text = if (item == -1) {
                "None"
            } else {
                item.toString()
            }
            setOnClickListener {
                onClick.invoke(item)
            }
        }

    }
}
package com.example.portal.modules.rocket


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.portal.databinding.RowRocketBinding

class RocketAdapter(private val onClick: (RocketItem) -> Unit) :
    RecyclerView.Adapter<RocketViewHolder>() {

    var items: List<RocketItem> = mutableListOf()
        set(value) {
            (field as MutableList).apply {
                clear()
                addAll(value)
            }
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RocketViewHolder(
            RowRocketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RocketViewHolder, position: Int) {
        holder.bindItem(items[position], onClick)

    }

}

class RocketViewHolder(private val viewBinding: RowRocketBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    @SuppressLint("SetTextI18n")
    fun bindItem(
        rocketItem: RocketItem,
        onClick: (RocketItem) -> Unit
    ) {
        viewBinding.apply {
            Glide.with(root.context)
                .load(rocketItem.missionPatch)
                .centerCrop()
                .into(imageView)
            textViewName.text = rocketItem.missionName + " - " + rocketItem.launchYear
            root.setOnClickListener {
                onClick.invoke(rocketItem)
            }
        }
    }
}

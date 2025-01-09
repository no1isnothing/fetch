package com.example.fetchinterview.ui

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.example.fetchinterview.data.NameItem
import com.example.fetchinterview.data.ViewItemBase
import com.example.fetchinterview.data.ViewItemName
import com.example.fetchinterview.data.ViewType
import com.example.fetchinterview.databinding.ItemNameBinding

/**
 * [RecyclerView.Adapter] that displays a list of [NameItem]s.
 */
class NameAdapter(
    private val values: List<ViewItemBase>
) : RecyclerView.Adapter<NameAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ItemNameBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        when(item.type) {
            ViewType.NAME -> {
                holder.idView.text = (item as ViewItemName).item.id.toString()
                holder.contentView.text = item.item.name
            }
            ViewType.LIST -> {

            }
        }
    }

    override fun getItemCount(): Int = values.size

    class ViewHolder(binding: ItemNameBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content
    }

}
package com.example.fetchinterview.ui

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.fetchinterview.data.NameItem
import com.example.fetchinterview.data.ViewItemBase
import com.example.fetchinterview.data.ViewItemList
import com.example.fetchinterview.data.ViewItemName
import com.example.fetchinterview.data.ViewType
import com.example.fetchinterview.databinding.ItemNameBinding

/**
 * [RecyclerView.Adapter] that displays a list of [ViewItemList]s and [ViewItemName]s.
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
                // TODO #1: Pull color from theme
                holder.cardView.setCardBackgroundColor( Color.parseColor("#03DAC6"))
                holder.contentView.setTextColor(Color.BLACK)
                //holder.listIdView.text = item.item.listId.toString()

            }
            ViewType.LIST -> {
                holder.idView.text = ""
                val id = (item as ViewItemList).listId.toString()
                // TODO #2: Use a different layout here. Move String to Resource
                holder.contentView.text = "********** List Id: $id **********"
                // TODO #1: Pull color from theme
                holder.cardView.setCardBackgroundColor(Color.parseColor("#6200EE"))
                holder.contentView.setTextColor(Color.WHITE)
                //holder.listIdView.text = ""
            }
        }
    }

    override fun getItemCount(): Int = values.size

    class ViewHolder(binding: ItemNameBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content
        val cardView: CardView = binding.nameCardView
       // val listIdView: TextView = binding.listId
    }

}
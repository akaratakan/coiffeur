package com.exampleui.coiffeur

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.exampleui.coiffeur.databinding.ItemHeaderBinding
import com.exampleui.coiffeur.databinding.ItemServiceBinding


/**
 * Created by atakanakar on 18.12.2021.
 * TransferChain
 */

const val HEADER_ITEM = 0
const val SERVICE_ITEM = 1

class ServicesAdapter(val onInfoClicked: (String) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(),StickHeaderItemDecoration.StickyHeaderInterface {

    private val itemList = arrayListOf<CoiffeurServices>()

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(itemList: List<CoiffeurServices>) {
        this.itemList.clear()
        this.itemList.addAll(itemList)
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(val binding: ItemServiceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CoiffeurServices) {
            binding.serviceName.text = item.name
            binding.serviceDuration.text = item.time
            binding.container.setOnClickListener {
                binding.imageCheckBox.performClick()
            }
            binding.infoButton.setOnClickListener {
                onInfoClicked(item.description)
            }
        }
    }

    inner class HeaderViewHolder(val binding: ItemHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: String) {
            binding.headerChar.text = category
        }
    }

    override fun getItemViewType(position: Int): Int {
        return itemList[position].viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HEADER_ITEM -> {
                HeaderViewHolder(
                    ItemHeaderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            SERVICE_ITEM -> {
                ItemViewHolder(
                    ItemServiceBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                throw IllegalStateException("Unknown view type")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            HEADER_ITEM -> {
                val headerHolder: HeaderViewHolder = holder as HeaderViewHolder
                headerHolder.bind(itemList[position].category)
            }
            SERVICE_ITEM -> {
                val serviceHolder: ItemViewHolder = holder as ItemViewHolder
                serviceHolder.bind(itemList[position])
            }
            else -> throw IllegalStateException("Unknown view type")
        }
    }

    override fun getItemCount(): Int = itemList.size

    override fun getHeaderPositionForItem(itemPosition: Int): Int {
        var headerPosition = 0
        var position = itemPosition
        do {
            if (isHeader(itemPosition)) {
                headerPosition = itemPosition
                break
            }
            position -= 1
        } while (position >= 0)
        return headerPosition
    }

    override fun getHeaderLayout(headerPosition: Int): Int = R.layout.item_header


    override fun bindHeaderData(header: View?, headerPosition: Int) {

    }

    override fun isHeader(itemPosition: Int): Boolean {
        return itemList[itemPosition].viewType == HEADER_ITEM;
    }

}
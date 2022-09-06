package com.shakib.pagingexample.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shakib.pagingexample.R
import com.shakib.pagingexample.databinding.ItemQuoteBinding
import com.shakib.pagingexample.models.Quote

class QuotePagingAdapter : PagingDataAdapter<Quote,
        QuotePagingAdapter.QuoteViewHolder>(COMPARATOR) {


    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.quote.text = item.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_quote, parent, false)
        return QuoteViewHolder(view)
    }

    inner class QuoteViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val quote = itemView.findViewById<TextView>(R.id.quoteTv)


    }


    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Quote>() {
            override fun areContentsTheSame(oldItem: Quote, newItem: Quote): Boolean {
                return oldItem._id == newItem._id
            }

            override fun areItemsTheSame(oldItem: Quote, newItem: Quote): Boolean {

                return oldItem == newItem
            }
        }
    }
}
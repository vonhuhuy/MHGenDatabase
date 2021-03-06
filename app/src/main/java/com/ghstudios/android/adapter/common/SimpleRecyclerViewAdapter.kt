package com.ghstudios.android.adapter.common

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlinx.android.extensions.LayoutContainer
import java.util.*

/**
 * A simple container-only viewholder used by SimpleListDelegate and SimpleRecyclerViewAdapter.
 * Using a viewholder when using KTX allows caching to work.
 */
class SimpleViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer {
    val context get() = itemView.context
    val resources get() = itemView.resources
}

/**
 * Defines an adapter for a simple item meant to be used via KTX.
 * For an adapter with multiple items, use a delegate instead.
 * (Once SimpleListDelegate is made, it'll become easy to swap between the two)
 */
abstract class SimpleRecyclerViewAdapter<T>: RecyclerView.Adapter<SimpleViewHolder>() {
    protected abstract fun onCreateView(parent: ViewGroup): View
    protected abstract fun bindView(viewHolder: SimpleViewHolder, data: T)

    // internal modifiable list
    private val itemSource = mutableListOf<T>()

    /**
     * A list of items contained in this adapter. Cannot be modified directly
     */
    val items = Collections.unmodifiableList(itemSource)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        val v = onCreateView(parent)
        return SimpleViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        val item = items[position]
        bindView(holder, item)
    }

    /**
     * Updates the items in this adapter and calls notifyDataSetChanged
     */
    fun setItems(items: List<T>) {
        itemSource.clear()
        itemSource.addAll(items)
        notifyDataSetChanged()
    }
}
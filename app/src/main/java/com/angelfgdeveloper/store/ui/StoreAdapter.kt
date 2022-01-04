package com.angelfgdeveloper.store.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.angelfgdeveloper.store.R
import com.angelfgdeveloper.store.core.OnClickListener
import com.angelfgdeveloper.store.data.model.StoreEntity
import com.angelfgdeveloper.store.databinding.ItemStoreBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class StoreAdapter(
    private var stores: MutableList<StoreEntity>,
    private var listener: OnClickListener
) : RecyclerView.Adapter<StoreAdapter.ViewHolder>() {

    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context

        val view = LayoutInflater.from(mContext).inflate(R.layout.item_store, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val store = stores[position]

        with(holder) {
            setListener(store)
            binding.tvName.text = store.name
            binding.cbFavorite.isChecked = store.isFavorite

            Glide.with(mContext)
                .load(store.photoUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(binding.ivPhoto)
        }
    }

    override fun getItemCount(): Int = stores.size

    fun setStores(stores: MutableList<StoreEntity>) {
        this.stores = stores
        this.notifyDataSetChanged()
    }

    fun add(storeEntity: StoreEntity) {
        if (!stores.contains(storeEntity)) {
            stores.add(storeEntity)
            notifyItemInserted(stores.size - 1)
        }
    }

    fun update(storeEntity: StoreEntity) {
        val index = stores.indexOf(storeEntity)

        if (index != -1) {
            stores.set(index, storeEntity)
            notifyItemChanged(index)
        }
    }

    fun delete(storeEntity: StoreEntity) {
        val index = stores.indexOf(storeEntity)

        if (index != -1) {
            stores.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val binding = ItemStoreBinding.bind(v)

        fun setListener(storeEntity: StoreEntity) {

            with(binding.root) {
                setOnClickListener { listener.onClick(storeEntity) }
                setOnLongClickListener {
                    listener.onDeleteStore(storeEntity)
                    true
                }
            }

            binding.cbFavorite.setOnClickListener { listener.onFavoriteStore(storeEntity) }
        }
    }
}
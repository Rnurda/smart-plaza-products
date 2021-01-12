package com.example.productssearch.ui.products

import android.text.SpannableString
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.productssearch.R
import com.example.productssearch.data.SmartPlazaProduct
import com.example.productssearch.databinding.ItemSmartplazaProductBinding

class SmartPlazaProductAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<SmartPlazaProduct, SmartPlazaProductAdapter.ProductViewHolder>(
        PRODUCT_COMPARATOR
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemSmartplazaProductBinding
                .inflate(LayoutInflater
                    .from(parent.context), parent, false)

        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }

    }

    inner class ProductViewHolder(private val binding: ItemSmartplazaProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init{
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if(position!=RecyclerView.NO_POSITION){
                    val item = getItem(position)
                    if(item!=null){
                        listener.onItemClick(item)
                    }
                }
            }
        }

        fun bind(product: SmartPlazaProduct) {
            binding.apply {
                Glide.with(itemView)
                    .load("https://api.smartplaza.kz/mp/products/photos/${product.photo_1}")
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(imageView)
                textViewProductName.text = product.name
                if(product.promoPrice!=null || product.promoPrice != 0) {
                    textViewProductPrice.text = product.price.toString()+"тг"
                }else{
                    textViewProductDiscountPrice.text = product.promoPrice.toString()+"тг"
                    val spannableString1 = SpannableString(product.price.toString()+"тг")
                    spannableString1.setSpan(StrikethroughSpan(),0,product.price.toString().length,0)
                    textViewProductPrice.text = spannableString1
                }

            }
        }
    }

    interface OnItemClickListener{
        fun onItemClick(product: SmartPlazaProduct)
    }

    companion object {
        private val PRODUCT_COMPARATOR = object : DiffUtil.ItemCallback<SmartPlazaProduct>() {
            override fun areItemsTheSame(
                oldItem: SmartPlazaProduct,
                newItem: SmartPlazaProduct
            ) = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: SmartPlazaProduct,
                newItem: SmartPlazaProduct
            ) = oldItem == newItem
        }
    }

}
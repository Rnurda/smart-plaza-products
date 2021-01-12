package com.example.productssearch.ui.details

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.productssearch.R
import com.example.productssearch.databinding.FragmentDetailsBinding

class DetatilsFragment: Fragment(R.layout.fragment_details) {

    private val args by navArgs<DetatilsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentDetailsBinding.bind(view)

        binding.apply {
            val product = args.photo

            Glide.with(this@DetatilsFragment)
                .load("https://api.smartplaza.kz/mp/products/photos/${product.photo_1}")
                .error(R.drawable.ic_error)
                .listener(object :RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        textView3.isVisible = product.shortDescription != null
                        textViewDesc.isVisible = product.shortDescription != null
                        return false
                    }

                })
                .into(imageView)

            textViewCat.text = product.superCategory?.name.toString()
            textViewName.text = product.name.toString()
            textViewPrice.text = product.price.toString()+"тг"
            textViewDesc.text = product.shortDescription.toString()



        }
    }
}
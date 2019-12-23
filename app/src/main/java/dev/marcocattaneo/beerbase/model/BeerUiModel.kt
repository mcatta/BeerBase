package dev.marcocattaneo.beerbase.model

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.io.Serializable

data class BeerUiModel(val id: Int, val name: String, val beerImage: String?): Serializable {

    companion object {
        @BindingAdapter("beerImage")
        @JvmStatic
        fun loadImage(view: ImageView, imageUrl: String?) {
            imageUrl?.let {
                Glide.with(view.context)
                    .load(imageUrl)
                    .into(view)
            }
        }
    }

}
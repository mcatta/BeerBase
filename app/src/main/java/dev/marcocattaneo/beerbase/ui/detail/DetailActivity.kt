package dev.marcocattaneo.beerbase.ui.detail

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import dagger.android.AndroidInjection
import dev.marcocattaneo.beerbase.R
import dev.marcocattaneo.beerbase.databinding.ActivityDetailBinding
import dev.marcocattaneo.beerbase.ui.BaseActivity
import dev.marcocattaneo.beerbase.utils.DaggerViewModelFactory
import javax.inject.Inject

class DetailActivity: BaseActivity() {

    @Inject
    lateinit var daggerAndroidViewModelFactory: DaggerViewModelFactory

    private val binding: ActivityDetailBinding by lazy { DataBindingUtil.setContentView<ActivityDetailBinding>(this, R.layout.activity_detail) }

    private val detailViewModel: DetailViewModel by lazy { ViewModelProviders.of(this, this.daggerAndroidViewModelFactory).get(DetailViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)


        initUI()
    }

    private fun initUI() {

    }


}
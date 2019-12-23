package dev.marcocattaneo.beerbase.ui.detail

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import dagger.android.AndroidInjection
import dev.marcocattaneo.beerbase.R
import dev.marcocattaneo.beerbase.databinding.ActivityDetailBinding
import dev.marcocattaneo.beerbase.model.BeerUiModel
import dev.marcocattaneo.beerbase.model.FullBeerUiModel
import dev.marcocattaneo.beerbase.ui.BaseActivity
import dev.marcocattaneo.beerbase.utils.DaggerViewModelFactory
import dev.marcocattaneo.beerbase.utils.LiveDataResult
import javax.inject.Inject

class DetailActivity: BaseActivity() {

    companion object {
        const val ARG_BEER = "arg.detail.beermodel"
    }

    @Inject
    lateinit var daggerAndroidViewModelFactory: DaggerViewModelFactory

    private val binding: ActivityDetailBinding by lazy { DataBindingUtil.setContentView<ActivityDetailBinding>(this, R.layout.activity_detail) }

    private val detailViewModel: DetailViewModel by lazy { ViewModelProviders.of(this, this.daggerAndroidViewModelFactory).get(DetailViewModel::class.java) }

    private val beerObserver = Observer<LiveDataResult<FullBeerUiModel>> {
        when (it) {
            is LiveDataResult.Loading -> {

            }
            is LiveDataResult.Success -> {

            }
            is LiveDataResult.Error -> {

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)

        initUI()

        this.detailViewModel.beerDetailLiveData.observe(this, this.beerObserver)

        val beer: BeerUiModel? = if (intent.hasExtra(ARG_BEER)) intent.getSerializableExtra(ARG_BEER) as BeerUiModel else null
        beer?.let {
            this.detailViewModel.getBeer(it.id)
        }
    }

    private fun initUI() {

    }


}
package dev.marcocattaneo.beerbase.ui.detail

import android.os.Build
import android.os.Bundle
import android.transition.Transition
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import dagger.android.AndroidInjection
import dev.marcocattaneo.beerbase.BR
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
        const val ARG_VIEW_NAME_HEADER_IMAGE = ""
    }

    @Inject
    lateinit var daggerAndroidViewModelFactory: DaggerViewModelFactory

    private val binding: ActivityDetailBinding by lazy { DataBindingUtil.setContentView<ActivityDetailBinding>(this, R.layout.activity_detail) }

    private val detailViewModel: DetailViewModel by lazy { ViewModelProviders.of(this, this.daggerAndroidViewModelFactory).get(DetailViewModel::class.java) }

    private var beerUiModel: BeerUiModel? = null

    private val beerObserver = Observer<LiveDataResult<FullBeerUiModel>> {
        when (it) {
            is LiveDataResult.Loading -> {
            }
            is LiveDataResult.Success -> {
                loadData(it.result)
            }
            is LiveDataResult.Error -> {

            }
        }
    }

    private fun loadData(model: FullBeerUiModel) {
        this.binding.setVariable(BR.fullBeerModel, model)
        this.binding.container.transitionToEnd()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)

        beerUiModel = if (intent.hasExtra(ARG_BEER)) intent.getSerializableExtra(ARG_BEER) as BeerUiModel else null
        ViewCompat.setTransitionName(this.binding.image, ARG_VIEW_NAME_HEADER_IMAGE)

        initUI()

        this.detailViewModel.beerDetailLiveData.observe(this, this.beerObserver)

        beerUiModel?.let {
            this.detailViewModel.getBeer(it.id)
        }
    }

    private fun initUI() {
        setSupportActionBar(this.binding.toolbar)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && addTransitionListener()) {
            loadFullSizeImage()
        } else {
            loadFullSizeImage()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    @RequiresApi(21)
    private fun addTransitionListener() : Boolean {
        val transition = window.sharedElementEnterTransition

        transition?.addListener(object: Transition.TransitionListener {
            override fun onTransitionEnd(transition: Transition) {
                // As the transition has ended, we can now load the full-size image
                loadFullSizeImage()

                // Make sure we remove ourselves as a listener
                transition.removeListener(this)
            }

            override fun onTransitionStart(transition: Transition) {
                // No-op
            }

            override fun onTransitionCancel(transition: Transition) {
                // Make sure we remove ourselves as a listener
                transition.removeListener(this)
            }

            override fun onTransitionPause(transition: Transition) {
                // No-op
            }

            override fun onTransitionResume(transition: Transition) {
                // No-op
            }
        })

        return transition != null
    }

    private fun loadFullSizeImage() {
        Glide.with(this.binding.image.context)
            .load(beerUiModel?.beerImage)
            .into(this.binding.image)

    }

}
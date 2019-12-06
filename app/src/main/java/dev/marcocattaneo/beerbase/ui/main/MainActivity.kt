package dev.marcocattaneo.beerbase.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.android.AndroidInjection
import dev.marcocattaneo.beerbase.R
import dev.marcocattaneo.beerbase.databinding.ActivityMainBinding
import dev.marcocattaneo.beerbase.databinding.AdapterListRowBinding
import dev.marcocattaneo.beerbase.model.BeerUiModel
import dev.marcocattaneo.beerbase.ui.BaseActivity
import dev.marcocattaneo.beerbase.ui.utils.ListDecorator
import dev.marcocattaneo.beerbase.utils.DaggerViewModelFactory
import dev.marcocattaneo.beerbase.utils.LiveDataResult
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var daggerAndroidViewModelFactory: DaggerViewModelFactory

    private val diffUtil = object : DiffUtil.ItemCallback<BeerUiModel>() {
        override fun areItemsTheSame(oldItem: BeerUiModel, newItem: BeerUiModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BeerUiModel, newItem: BeerUiModel): Boolean {
            return oldItem.id == newItem.id
        }

    }

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProviders.of(
            this,
            this.daggerAndroidViewModelFactory
        ).get(MainViewModel::class.java)
    }

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        )
    }

    private val observer = Observer<LiveDataResult<List<BeerUiModel>>> {
        when (it) {
            is LiveDataResult.Loading -> {
                binding.swipeToRefresh.isRefreshing = true
            }
            is LiveDataResult.Success -> {
                binding.swipeToRefresh.isRefreshing = false
                (binding.list.adapter as BeersAdapter).submitList(it.result)
            }
            is LiveDataResult.Error -> {
                binding.swipeToRefresh.isRefreshing = false
                showSnackbar(it.err.message)
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)

        initUI()

        mainViewModel.fetchResult.observe(this, this.observer)

        mainViewModel.searchBeer("punk ipa")
    }

    private fun showSnackbar(message: String?) {
        message?.let {
            Snackbar.make(this.binding.container, it, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun initUI() {
        binding.list.apply {
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(ListDecorator(resources.getDimensionPixelSize(R.dimen.recycler_view_spacing)))
            adapter = BeersAdapter(diffUtil)
        }
        binding.swipeToRefresh.setOnRefreshListener { mainViewModel.searchBeer("punk ipa") }
    }

    class BeersAdapter(diffUtilCallback: DiffUtil.ItemCallback<BeerUiModel>) :
        ListAdapter<BeerUiModel, BeersAdapter.ListItemViewHolder>(diffUtilCallback) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ListItemViewHolder(
            AdapterListRowBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

        inner class ListItemViewHolder(val adapterListRowBinding: AdapterListRowBinding) :
            RecyclerView.ViewHolder(adapterListRowBinding.root) {

            fun bind(value: String) {
                this.adapterListRowBinding.title.text = value
            }

        }

        override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
            holder.bind(getItem(position).name)
        }
    }
}

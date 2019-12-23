package dev.marcocattaneo.beerbase.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.*
import com.arlib.floatingsearchview.FloatingSearchView
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion
import com.google.android.material.snackbar.Snackbar
import dagger.android.AndroidInjection
import dev.marcocattaneo.beerbase.BR
import dev.marcocattaneo.beerbase.R
import dev.marcocattaneo.beerbase.databinding.ActivityMainBinding
import dev.marcocattaneo.beerbase.databinding.AdapterListRowBinding
import dev.marcocattaneo.beerbase.model.BeerUiModel
import dev.marcocattaneo.beerbase.ui.BaseActivity
import dev.marcocattaneo.beerbase.ui.detail.DetailActivity
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

    private val adapterDelegate = object: AdapterDelegate {
        override fun onClick(beerUiModel: BeerUiModel) {
            val intent = Intent(this@MainActivity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.ARG_BEER, beerUiModel)
            startActivity(intent)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)

        initUI()

        this.mainViewModel.fetchResult.observe(this, this.observer)

        this.binding.floatingSearchView.setOnSearchListener(object :
            FloatingSearchView.OnSearchListener {
            override fun onSearchAction(currentQuery: String?) {
                this@MainActivity.mainViewModel.searchBeer(currentQuery ?: "")
            }

            override fun onSuggestionClicked(searchSuggestion: SearchSuggestion?) {

            }

        })
    }

    private fun showSnackbar(message: String?) {
        message?.let {
            Snackbar.make(this.binding.container, it, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun initUI() {
        val spanCount = resources.getInteger(R.integer.span_count)
        binding.list.apply {
            layoutManager =
                GridLayoutManager(this@MainActivity, spanCount, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(
                ListDecorator(
                    resources.getDimensionPixelSize(R.dimen.recycler_view_spacing),
                    220,
                    spanCount
                )
            )
            adapter = BeersAdapter(diffUtil, adapterDelegate)
        }

        binding.list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    binding.container.transitionToEnd()
                } else {
                    binding.container.transitionToStart()
                }
            }
        })

        binding.swipeToRefresh.setOnRefreshListener { mainViewModel.searchBeer(this.binding.floatingSearchView.query) }
    }

    class BeersAdapter(diffUtilCallback: DiffUtil.ItemCallback<BeerUiModel>, private val delegate: AdapterDelegate) :
        ListAdapter<BeerUiModel, BeersAdapter.ListItemViewHolder>(diffUtilCallback) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ListItemViewHolder(
            AdapterListRowBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

        inner class ListItemViewHolder(private val adapterListRowBinding: AdapterListRowBinding) :
            RecyclerView.ViewHolder(adapterListRowBinding.root) {

            fun bind(beerUiModel: BeerUiModel) {
                this.adapterListRowBinding.setVariable(BR.model, beerUiModel)
                // Handle click event
                this.adapterListRowBinding.card.setOnClickListener {
                    delegate.onClick(beerUiModel)
                }
            }

        }

        override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
            holder.bind(getItem(position))
        }
    }

    interface AdapterDelegate {
        fun onClick(beerUiModel: BeerUiModel)
    }
}

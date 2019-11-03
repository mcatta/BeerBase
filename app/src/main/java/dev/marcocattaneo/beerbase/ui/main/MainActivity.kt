package dev.marcocattaneo.beerbase.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.android.AndroidInjection
import dev.marcocattaneo.beerbase.R
import dev.marcocattaneo.beerbase.databinding.ActivityMainBinding
import dev.marcocattaneo.beerbase.databinding.AdapterListRowBinding
import dev.marcocattaneo.beerbase.model.BeerModel
import dev.marcocattaneo.beerbase.ui.BaseActivity
import dev.marcocattaneo.beerbase.utils.DaggerViewModelFactory
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var daggerAndroidViewModelFactory: DaggerViewModelFactory

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProviders.of(
            this,
            this.daggerAndroidViewModelFactory
        ).get(MainViewModel::class.java)
    }

    private val binding: ActivityMainBinding by lazy { DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main) }

    private val observer = Observer<List<BeerModel>> {
        (binding.list.adapter as ListAdapter).items = it
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)

        binding.list.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            adapter = ListAdapter()
        }

        mainViewModel.fetchResult.observe(this, this.observer)

        mainViewModel.fetch()
    }

    class ListAdapter: RecyclerView.Adapter<ListAdapter.ListItemViewHolder>() {

        var items: List<BeerModel> = listOf()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ListItemViewHolder(AdapterListRowBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))

        override fun getItemCount() = items.size

        override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
            holder.bind(items[position].id)
        }

        inner class ListItemViewHolder(val adapterListRowBinding: AdapterListRowBinding): RecyclerView.ViewHolder(adapterListRowBinding.root) {

            fun bind(value: String) {
                this.adapterListRowBinding.title.text = value
            }

        }
    }
}

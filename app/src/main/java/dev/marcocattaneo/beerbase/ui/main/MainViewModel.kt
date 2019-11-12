package dev.marcocattaneo.beerbase.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.marcocattaneo.beerbase.utils.LiveDataResult
import dev.marcocattaneo.data.model.BeerModel
import dev.marcocattaneo.data.repository.BeerRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val beerRepository: BeerRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) : ViewModel() {

    val fetchResult = MutableLiveData<LiveDataResult<List<BeerModel>>>()

    fun searchBeer(query: String) {
        fetchResult.value = LiveDataResult.loading()
        viewModelScope.launch {

            val data = withContext(coroutineDispatcher) {
                try {
                    this@MainViewModel.beerRepository.searchBeer(query)
                } catch (e: Exception) {
                    null
                }
            }

            data?.let {
                fetchResult.value = LiveDataResult.success(data)
            } ?: run {
                fetchResult.value = LiveDataResult.error(NullPointerException())
            }

        }
    }

    override fun onCleared() {
        super.onCleared()
    }

}
package dev.marcocattaneo.beerbase.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.marcocattaneo.beerbase.utils.LiveDataResult
import dev.marcocattaneo.beerbase.utils.LiveDataResultStatus
import dev.marcocattaneo.data.model.BeerModel
import dev.marcocattaneo.data.repository.BeerRepository
import dev.marcocattaneo.data.utils.CoroutineResponse
import kotlinx.coroutines.*
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val beerRepository: BeerRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val job = SupervisorJob()

    val fetchResult = MutableLiveData<LiveDataResult<List<BeerModel>>>()

    fun searchBeer(query: String) {
        viewModelScope.launch {
            fetchResult.value = LiveDataResult.loading()

            val res = loadData(query)
            when {
                res.result != null -> {
                    fetchResult.value = LiveDataResult(LiveDataResultStatus.SUCCESS, res.result)
                }
                res.error != null -> {
                    fetchResult.value = LiveDataResult(LiveDataResultStatus.ERROR, res.result, res.error)
                }
                else -> {}
            }

        }
    }

    private suspend fun loadData(query: String) = withContext(coroutineDispatcher) {
        try {
            CoroutineResponse.result(beerRepository.searchBeer(query))
        } catch (e: Exception) {
            CoroutineResponse.error<List<BeerModel>>(e)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}
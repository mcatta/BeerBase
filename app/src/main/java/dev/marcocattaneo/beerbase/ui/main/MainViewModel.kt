package dev.marcocattaneo.beerbase.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.marcocattaneo.beerbase.utils.Error
import dev.marcocattaneo.beerbase.utils.LiveDataResult
import dev.marcocattaneo.beerbase.utils.LiveDataResultStatus
import dev.marcocattaneo.beerbase.utils.Success
import dev.marcocattaneo.data.repository.BeerRepositoryImpl
import dev.marcocattaneo.domain.models.BeerModel
import kotlinx.coroutines.*
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val beerRepositoryImpl: BeerRepositoryImpl,
    private val coroutineDispatcher: CoroutineDispatcher
) : ViewModel() {

    val fetchResult = MutableLiveData<LiveDataResult<List<BeerModel>>>()

    fun searchBeer(query: String) {
        viewModelScope.launch {
            fetchResult.value = LiveDataResult.loading()

            val res = loadData(query)
            when(res) {
                is Success<*> -> {
                    fetchResult.value = LiveDataResult(LiveDataResultStatus.SUCCESS, res.result as? List<BeerModel>)
                }
                is Error -> {
                    fetchResult.value = LiveDataResult(LiveDataResultStatus.ERROR, listOf(), res.error)
                }
                else -> {}
            }

        }
    }

    private suspend fun loadData(query: String) = withContext(coroutineDispatcher) {
        try {
            Success(beerRepositoryImpl.searchBeer(query))
        } catch (e: Exception) {
            Error(e)
        }
    }

}
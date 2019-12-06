package dev.marcocattaneo.beerbase.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.marcocattaneo.beerbase.utils.CoroutineResponse
import dev.marcocattaneo.beerbase.utils.LiveDataResult
import dev.marcocattaneo.data.interactors.SearchBeerUseCase
import dev.marcocattaneo.domain.models.BeerModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val searchBeerUseCase: SearchBeerUseCase,
    private val coroutineDispatcher: CoroutineDispatcher
) : ViewModel() {

    val fetchResult = MutableLiveData<LiveDataResult<List<BeerModel>>>()

    fun searchBeer(query: String) {
        viewModelScope.launch {
            fetchResult.value = LiveDataResult.Loading()

            val res = loadData(query)
            when(res) {
                is CoroutineResponse.Success<*> -> {
                    fetchResult.value = LiveDataResult.Success(res.result as List<BeerModel>)
                }
                is CoroutineResponse.Error -> {
                    fetchResult.value = LiveDataResult.Error(res.error)
                }
                else -> {}
            }

        }
    }

    private suspend fun loadData(query: String): CoroutineResponse = withContext(coroutineDispatcher) {
        try {
            CoroutineResponse.Success(searchBeerUseCase.execute(query))
        } catch (e: Exception) {
            CoroutineResponse.Error(e)
        }
    }

}
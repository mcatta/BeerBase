package dev.marcocattaneo.beerbase.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.marcocattaneo.beerbase.mapper.BeerUiMapper
import dev.marcocattaneo.beerbase.model.BeerUiModel
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
    private val coroutineDispatcher: CoroutineDispatcher,
    private val beerUiMapper: BeerUiMapper
) : ViewModel() {

    val fetchResult = MutableLiveData<LiveDataResult<List<BeerUiModel>>>()

    fun searchBeer(query: String) {
        viewModelScope.launch {
            fetchResult.value = LiveDataResult.Loading()

            when(val res = loadData(query)) {
                is CoroutineResponse.Success<*> -> {
                    val beers = res.result as List<BeerModel>

                    fetchResult.value = LiveDataResult.Success(beers.map { beerUiMapper.map(it) })
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
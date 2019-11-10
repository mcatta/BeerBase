package dev.marcocattaneo.beerbase.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.marcocattaneo.beerbase.data.BeerRepository
import dev.marcocattaneo.beerbase.model.BeerModel
import dev.marcocattaneo.beerbase.utils.LiveDataResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.NullPointerException
import javax.inject.Inject

class MainViewModel @Inject constructor(private val beerRepository: BeerRepository) : ViewModel() {

    val fetchResult = MutableLiveData<LiveDataResult<List<BeerModel>>>()

    fun searchBeer(query: String) {
        fetchResult.value = LiveDataResult.loading()
        viewModelScope.launch {

            val data = async(Dispatchers.Default) {
                try {
                    this@MainViewModel.beerRepository.searchBeer(query)
                } catch (e: Exception) {
                    null
                }
            }.await()

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
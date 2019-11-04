package dev.marcocattaneo.beerbase.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.marcocattaneo.beerbase.data.GithubService
import dev.marcocattaneo.beerbase.model.BeerModel
import dev.marcocattaneo.beerbase.utils.LiveDataResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val githubService: GithubService): ViewModel() {

    val fetchResult = MutableLiveData<LiveDataResult<List<BeerModel>>>()

    fun fetch() {
        viewModelScope.launch {

            try {
                val data = async(Dispatchers.Default) {
                    this@MainViewModel.githubService.fetchRepositories("mcatta")
                }.await()

                fetchResult.value = LiveDataResult.success(data)
            } catch (e: Exception) {

            }

        }
    }

}
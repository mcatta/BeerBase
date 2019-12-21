package dev.marcocattaneo.beerbase.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.marcocattaneo.beerbase.mapper.FullBeerUiMapper
import dev.marcocattaneo.beerbase.model.FullBeerUiModel
import dev.marcocattaneo.beerbase.utils.CoroutineResponse
import dev.marcocattaneo.beerbase.utils.LiveDataResult
import dev.marcocattaneo.data.interactors.GetBeerDetailUseCase
import dev.marcocattaneo.domain.exception.RecordNotFoundException
import dev.marcocattaneo.domain.models.BeerModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val getBeerDetailUseCase: GetBeerDetailUseCase,
    private val coroutineDispatcher: CoroutineDispatcher,
    private val fullBeerUiMapper: FullBeerUiMapper
) : ViewModel() {

    private val _beerDetailLiveData = MutableLiveData<LiveDataResult<FullBeerUiModel>>()

    val beerDetailLiveData: LiveData<LiveDataResult<FullBeerUiModel>>
        get() = this._beerDetailLiveData

    /**
     * Get Beer information from API
     * @param id beer ID
     */
    fun getBeer(id: Int) {

        viewModelScope.launch {
            _beerDetailLiveData.value = LiveDataResult.Loading()

            when (val result = loadData(id)) {
                is CoroutineResponse.Success<*> -> {
                    val res = result.result as? BeerModel
                    res?.let {
                        _beerDetailLiveData.value = LiveDataResult.Success(fullBeerUiMapper.map(res))
                    } ?: kotlin.run {
                        _beerDetailLiveData.value = LiveDataResult.Error(RecordNotFoundException("Beer with ID $id not found"))
                    }

                }
                is CoroutineResponse.Error -> {
                    _beerDetailLiveData.value = LiveDataResult.Error(result.error)
                }
            }
        }
    }

    private suspend fun loadData(id: Int) : CoroutineResponse = withContext(this.coroutineDispatcher) {
        try {
            CoroutineResponse.Success(getBeerDetailUseCase.execute(id))
        } catch (e: Exception) {
            CoroutineResponse.Error(e)
        }
    }

}
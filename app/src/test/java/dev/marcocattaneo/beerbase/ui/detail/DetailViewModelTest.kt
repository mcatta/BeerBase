package dev.marcocattaneo.beerbase.ui.detail

import Boil_volume
import Fermentation
import Ingredients
import Method
import Temp
import Volume
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dev.marcocattaneo.beerbase.BaseTest
import dev.marcocattaneo.beerbase.CoroutinesTestRule
import dev.marcocattaneo.beerbase.mapper.FullBeerUiMapper
import dev.marcocattaneo.beerbase.utils.LiveDataResult
import dev.marcocattaneo.data.interactors.GetBeerDetailUseCase
import dev.marcocattaneo.domain.exception.RecordNotFoundException
import dev.marcocattaneo.domain.models.BeerModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.lang.IllegalStateException

@RunWith(JUnit4::class)
class DetailViewModelTest: BaseTest() {

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var detailViewModel: DetailViewModel

    @RelaxedMockK
    lateinit var getBeerDetailUseCase: GetBeerDetailUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        detailViewModel = DetailViewModel(getBeerDetailUseCase, coroutinesTestRule.testDispatcher, FullBeerUiMapper())
    }

    @Test
    fun testSuccessfullyApiResponse()  = coroutinesTestRule.testDispatcher.runBlockingTest {
        val validId = 123

        detailViewModel.beerDetailLiveData.observeForever {  }

        coEvery { getBeerDetailUseCase.execute(validId) } returns mockBeerModel(validId)

        detailViewModel.getBeer(validId)

        val result = detailViewModel.beerDetailLiveData.value

        // Not null
        isNotNull(result)
        // Is a success
        isTrue (result is LiveDataResult.Success)
        // The mapped value id is the same
        (result as LiveDataResult.Success).result.id isEqualTo validId
    }

    @Test
    fun testFailApiResponse() {
        val ex = IllegalStateException("Test fail")
        detailViewModel.beerDetailLiveData.observeForever {  }

        coEvery { getBeerDetailUseCase.execute(123) } throws ex

        detailViewModel.getBeer(123)

        val result = detailViewModel.beerDetailLiveData.value

        // Not null
        isNotNull(result)
        // Is a success
        isTrue (result is LiveDataResult.Error)
        // There is an exception
        (result as LiveDataResult.Error).err isEqualTo ex
    }

    @Test
    fun testEmptyApiResponse() {
        val validId = 123

        detailViewModel.beerDetailLiveData.observeForever {  }

        coEvery { getBeerDetailUseCase.execute(validId) } returns null

        detailViewModel.getBeer(validId)

        val result = detailViewModel.beerDetailLiveData.value

        // Not null
        isNotNull(result)
        // Is a success
        isTrue (result is LiveDataResult.Error)
        // The value is null
        isTrue(((result as LiveDataResult.Error).err) is RecordNotFoundException)
    }

    private fun mockBeerModel(id: Int): BeerModel {
        return BeerModel(id, "", "", "", "", "",
            0f, 0, 0f, 0f, 0f, 0f, 0f, 0f, Volume(0f, ""),
                Boil_volume(0f, ""), Method(listOf(), Fermentation(Temp(0f, "")), ""), Ingredients(listOf(), listOf(), ""),
                listOf(), "", ""
            )
    }

}
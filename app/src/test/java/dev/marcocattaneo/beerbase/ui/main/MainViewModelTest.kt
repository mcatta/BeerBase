package dev.marcocattaneo.beerbase.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dev.marcocattaneo.beerbase.BaseTest
import dev.marcocattaneo.beerbase.CoroutinesTestRule
import dev.marcocattaneo.beerbase.utils.LiveDataResult
import dev.marcocattaneo.data.interactors.SearchBeerUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MainViewModelTest: BaseTest() {

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var mainViewModel: MainViewModel

    @RelaxedMockK
    lateinit var searchBeerUseCase: SearchBeerUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mainViewModel = MainViewModel(searchBeerUseCase, this.coroutinesTestRule.testDispatcher)
    }

    @Test
    fun testSuccessfullyRequest() = coroutinesTestRule.testDispatcher.runBlockingTest {
        mainViewModel.fetchResult.observeForever { }
        coEvery { searchBeerUseCase.execute(any()) } returns listOf()

        isNull(mainViewModel.fetchResult.value)

        mainViewModel.searchBeer("beer")

        isNotNull(mainViewModel.fetchResult.value)
        assert(mainViewModel.fetchResult.value is LiveDataResult.Success)
    }

    @Test
    fun testNegativeRequest() = coroutinesTestRule.testDispatcher.runBlockingTest {
        mainViewModel.fetchResult.observeForever { }
        coEvery { searchBeerUseCase.execute(any()) } coAnswers { throw IllegalStateException() }

        isNull(mainViewModel.fetchResult.value)

        mainViewModel.searchBeer("beer")

        isNotNull(mainViewModel.fetchResult.value)
        assert(mainViewModel.fetchResult.value is LiveDataResult.Error)
    }

    @Test
    fun testMixedRequest() = coroutinesTestRule.testDispatcher.runBlockingTest {
        mainViewModel.fetchResult.observeForever { }
        coEvery { searchBeerUseCase.execute(any()) } coAnswers { throw IllegalStateException() }

        isNull(mainViewModel.fetchResult.value)

        mainViewModel.searchBeer("beer")

        isNotNull(mainViewModel.fetchResult.value)
        assert(mainViewModel.fetchResult.value is LiveDataResult.Error)

        coEvery { searchBeerUseCase.execute(any()) } returns listOf()

        mainViewModel.searchBeer("beer")

        assert(mainViewModel.fetchResult.value is LiveDataResult.Success)
    }

}
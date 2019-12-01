package dev.marcocattaneo.beerbase.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dev.marcocattaneo.beerbase.BaseTest
import dev.marcocattaneo.beerbase.CoroutinesTestRule
import dev.marcocattaneo.beerbase.utils.LiveDataResultStatus
import dev.marcocattaneo.data.repository.BeerRepositoryImpl
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
    lateinit var beerRepositoryImpl: BeerRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mainViewModel = MainViewModel(beerRepositoryImpl, this.coroutinesTestRule.testDispatcher)
    }

    @Test
    fun testSuccessfullyRequest() = coroutinesTestRule.testDispatcher.runBlockingTest {
        mainViewModel.fetchResult.observeForever { }
        coEvery { beerRepositoryImpl.searchBeer(any()) } returns listOf()

        isNotNull(mainViewModel.fetchResult.value)

        mainViewModel.searchBeer("beer")

        isNotNull(mainViewModel.fetchResult.value)
        mainViewModel.fetchResult.value?.status?.isEqualTo(LiveDataResultStatus.SUCCESS)
    }

    @Test
    fun testNegativeRequest() = coroutinesTestRule.testDispatcher.runBlockingTest {
        mainViewModel.fetchResult.observeForever { }
        coEvery { beerRepositoryImpl.searchBeer(any()) } coAnswers { throw IllegalStateException() }

        isNotNull(mainViewModel.fetchResult.value)

        mainViewModel.searchBeer("beer")

        isNotNull(mainViewModel.fetchResult.value)
        mainViewModel.fetchResult.value?.status isEqualTo LiveDataResultStatus.ERROR
    }

    @Test
    fun testMixedRequest() = coroutinesTestRule.testDispatcher.runBlockingTest {
        mainViewModel.fetchResult.observeForever { }
        coEvery { beerRepositoryImpl.searchBeer(any()) } coAnswers { throw IllegalStateException() }

        isNotNull(mainViewModel.fetchResult.value)

        mainViewModel.searchBeer("beer")

        isNotNull(mainViewModel.fetchResult.value)
        mainViewModel.fetchResult.value?.status isEqualTo LiveDataResultStatus.ERROR

        coEvery { beerRepositoryImpl.searchBeer(any()) } returns listOf()

        mainViewModel.searchBeer("beer")

        mainViewModel.fetchResult.value?.status isEqualTo LiveDataResultStatus.SUCCESS
    }

}
package dev.marcocattaneo.beerbase.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dev.marcocattaneo.beerbase.CoroutinesTestRule
import dev.marcocattaneo.beerbase.utils.LiveDataResultStatus
import dev.marcocattaneo.data.repository.BeerRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.Assert.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MainViewModelTest {

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var mainViewModel: MainViewModel

    @RelaxedMockK
    lateinit var beerRepository: BeerRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mainViewModel = MainViewModel(beerRepository, this.coroutinesTestRule.testDispatcher)
    }

    @Test
    fun testSuccessfullyRequest() = coroutinesTestRule.testDispatcher.runBlockingTest {
        mainViewModel.fetchResult.observeForever { }
        coEvery { beerRepository.searchBeer(any()) } returns listOf()

        assertNull(mainViewModel.fetchResult.value)

        mainViewModel.searchBeer("beer")

        assertNotNull(mainViewModel.fetchResult.value)
        assertEquals(mainViewModel.fetchResult.value?.status, LiveDataResultStatus.SUCCESS)
    }

    @Test
    fun testNegativeRequest() = coroutinesTestRule.testDispatcher.runBlockingTest {
        mainViewModel.fetchResult.observeForever { }
        coEvery { beerRepository.searchBeer(any()) } coAnswers { throw IllegalStateException() }

        assertNull(mainViewModel.fetchResult.value)

        mainViewModel.searchBeer("beer")

        assertNotNull(mainViewModel.fetchResult.value)
        assertEquals(mainViewModel.fetchResult.value?.status, LiveDataResultStatus.ERROR)
    }

    @Test
    fun testMixedRequest() = coroutinesTestRule.testDispatcher.runBlockingTest {
        mainViewModel.fetchResult.observeForever { }
        coEvery { beerRepository.searchBeer(any()) } coAnswers { throw IllegalStateException() }

        assertNull(mainViewModel.fetchResult.value)

        mainViewModel.searchBeer("beer")

        assertNotNull(mainViewModel.fetchResult.value)
        assertEquals(mainViewModel.fetchResult.value?.status, LiveDataResultStatus.ERROR)

        coEvery { beerRepository.searchBeer(any()) } returns listOf()

        mainViewModel.searchBeer("beer")

        assertEquals(mainViewModel.fetchResult.value?.status, LiveDataResultStatus.SUCCESS)
    }

}
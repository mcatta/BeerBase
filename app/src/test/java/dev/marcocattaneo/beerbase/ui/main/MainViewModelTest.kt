package dev.marcocattaneo.beerbase.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dev.marcocattaneo.beerbase.CoroutinesTestRule
import dev.marcocattaneo.beerbase.data.GithubService
import dev.marcocattaneo.beerbase.utils.LiveDataResult
import dev.marcocattaneo.beerbase.utils.LiveDataResultStatus
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
    lateinit var service: GithubService

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mainViewModel = MainViewModel(service)
    }

    @Test
    fun testSuccessfullyRequest() = coroutinesTestRule.testDispatcher.runBlockingTest {
        mainViewModel.fetchResult.observeForever {  }
        coEvery{ service.fetchRepositories(any()) } returns listOf()

        assertNull(mainViewModel.fetchResult.value)

        mainViewModel.fetch()

        assertNotNull(mainViewModel.fetchResult.value)
        assertEquals(mainViewModel.fetchResult.value?.status, LiveDataResultStatus.SUCCESS)
    }

}
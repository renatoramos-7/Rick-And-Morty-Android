package com.renatoramos.rickandmorty.episodes.presentation.ui.episodeslist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.renatoramos.rickandmorty.common.util.State
import com.renatoramos.rickandmorty.data.store.local.paperdb.provider.episodes.EpisodesProvider
import com.renatoramos.rickandmorty.data.store.remote.retrofit.api.episodes.EpisodesApi
import com.renatoramos.rickandmorty.data.store.repository.episodes.EpisodesRepository
import com.renatoramos.rickandmorty.domain.usecases.episodes.EpisodesUseCase
import com.renatoramos.rickandmorty.episodes.presentation.ui.episodeslist.adapter.datasource.EpisodesDataSource
import com.renatoramos.rickandmorty.episodes.presentation.ui.episodeslist.adapter.datasource.EpisodesDataSourceFactory
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class EpisodesListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val viewModel = EpisodesListViewModel(episodesUseCase = createEpisodesUseCase())

    @Test
    fun `getAllEpisodes initializes paged live data`() {
        viewModel.getAllEpisodes()

        assertNotNull(viewModel.reposListLiveData)
    }

    @Test
    fun `getState emits datasource state`() {
        viewModel.getAllEpisodes()
        val dataSource = getEpisodesDataSource()
        val observedStates = mutableListOf<State>()

        viewModel.getState().observeForever(observedStates::add)
        dataSource.state.value = State.DONE

        assertEquals(listOf(State.DONE), observedStates)
    }

    @Test
    fun `listIsEmpty returns true when paged list has not emitted yet`() {
        viewModel.getAllEpisodes()

        assertTrue(viewModel.listIsEmpty())
    }

    private fun getEpisodesDataSource(): EpisodesDataSource {
        val factory = getPrivateField(
            target = viewModel,
            fieldName = "episodesDataSourceFactory"
        ) as EpisodesDataSourceFactory

        return (factory.create() as EpisodesDataSource).also { dataSource ->
            factory.listRepositoriesDataSourceLiveData.value = dataSource
        }
    }

    private fun createEpisodesUseCase(): EpisodesUseCase {
        val api = java.lang.reflect.Proxy.newProxyInstance(
            EpisodesApi::class.java.classLoader,
            arrayOf(EpisodesApi::class.java)
        ) { _, _, _ -> error("EpisodesApi should not be called in this unit test") } as EpisodesApi

        return EpisodesUseCase(
            EpisodesRepository(
                episodesApi = api,
                episodesProvider = EpisodesProvider()
            )
        )
    }

    private fun getPrivateField(target: Any, fieldName: String): Any? {
        return target.javaClass.getDeclaredField(fieldName).apply {
            isAccessible = true
        }.get(target)
    }
}

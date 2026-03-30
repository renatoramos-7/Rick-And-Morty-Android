package com.renatoramos.rickandmorty.characters.presentation.ui.feature.characterslist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.renatoramos.rickandmorty.characters.presentation.ui.feature.characterslist.adapter.datasource.CharactersDataSource
import com.renatoramos.rickandmorty.characters.presentation.ui.feature.characterslist.adapter.datasource.CharactersDataSourceFactory
import com.renatoramos.rickandmorty.common.util.State
import com.renatoramos.rickandmorty.data.store.local.paperdb.provider.characters.CharacterProvider
import com.renatoramos.rickandmorty.data.store.remote.retrofit.api.characters.CharactersApi
import com.renatoramos.rickandmorty.data.store.repository.characters.CharactersRepository
import com.renatoramos.rickandmorty.domain.usecases.characters.CharactersUseCase
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class CharactersListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val viewModel = CharactersListViewModel(charactersUseCase = createCharactersUseCase())

    @Test
    fun `getAllCharacters initializes paged live data`() {
        viewModel.getAllCharacters()

        assertNotNull(viewModel.reposListLiveData)
    }

    @Test
    fun `getState emits datasource state`() {
        viewModel.getAllCharacters()
        val dataSource = getCharactersDataSource()
        val observedStates = mutableListOf<State>()

        viewModel.getState().observeForever(observedStates::add)
        dataSource.state.value = State.ERROR

        assertEquals(listOf(State.ERROR), observedStates)
    }

    @Test
    fun `listIsEmpty returns true when paged list has not emitted yet`() {
        viewModel.getAllCharacters()

        assertTrue(viewModel.listIsEmpty())
    }

    private fun getCharactersDataSource(): CharactersDataSource {
        val factory = getPrivateField(
            target = viewModel,
            fieldName = "charactersDataSourceFactory"
        ) as CharactersDataSourceFactory

        return (factory.create() as CharactersDataSource).also { dataSource ->
            factory.listRepositoriesDataSourceLiveData.value = dataSource
        }
    }

    private fun createCharactersUseCase(): CharactersUseCase {
        val api = java.lang.reflect.Proxy.newProxyInstance(
            CharactersApi::class.java.classLoader,
            arrayOf(CharactersApi::class.java)
        ) { _, _, _ -> error("CharactersApi should not be called in this unit test") } as CharactersApi

        return CharactersUseCase(
            CharactersRepository(
                charactersApi = api,
                characterProvider = CharacterProvider()
            )
        )
    }

    private fun getPrivateField(target: Any, fieldName: String): Any? {
        return target.javaClass.getDeclaredField(fieldName).apply {
            isAccessible = true
        }.get(target)
    }
}

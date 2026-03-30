package com.renatoramos.rickandmorty.characters.presentation.ui.feature.characterslist

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.lifecycle.switchMap
import com.renatoramos.rickandmorty.characters.presentation.ui.feature.characterslist.adapter.datasource.CharactersDataSource
import com.renatoramos.rickandmorty.characters.presentation.ui.feature.characterslist.adapter.datasource.CharactersDataSourceFactory
import com.renatoramos.rickandmorty.common.base.BaseViewModel
import com.renatoramos.rickandmorty.common.util.State
import com.renatoramos.rickandmorty.domain.usecases.characters.CharactersUseCase
import com.renatoramos.rickandmorty.domain.viewobject.characters.CharacterViewObject
import javax.inject.Inject

class CharactersListViewModel @Inject constructor(private val charactersUseCase: CharactersUseCase) :
    BaseViewModel() {

    lateinit var reposListLiveData: LiveData<PagedList<CharacterViewObject>>
    private lateinit var charactersDataSourceFactory: CharactersDataSourceFactory
    private val pageSize = 20

    fun getAllCharacters() {
        val compositeDisposable = getCompositeDisposable()

        charactersDataSourceFactory = CharactersDataSourceFactory(
            compositeDisposable,
            charactersUseCase
        )

        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize)
            .setEnablePlaceholders(false)
            .build()

        reposListLiveData = LivePagedListBuilder(
            charactersDataSourceFactory,
            config
        ).build()
    }

    fun getState(): LiveData<State> {
        return charactersDataSourceFactory.listRepositoriesDataSourceLiveData.switchMap(
            CharactersDataSource::state
        )
    }

    fun retry() {
        charactersDataSourceFactory.listRepositoriesDataSourceLiveData.value?.retry()
    }

    fun listIsEmpty(): Boolean {
        return reposListLiveData.value?.isEmpty() ?: true
    }

}

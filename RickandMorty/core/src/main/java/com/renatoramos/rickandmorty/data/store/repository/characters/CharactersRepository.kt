package com.renatoramos.rickandmorty.data.store.repository.characters

import com.renatoramos.rickandmorty.data.store.dto.characters.CharacterDTO
import com.renatoramos.rickandmorty.data.store.local.paperdb.provider.characters.CharacterProvider
import com.renatoramos.rickandmorty.data.store.remote.retrofit.api.characters.CharactersApi
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CharactersRepository @Inject constructor(
    private val charactersApi: CharactersApi,
    private val characterProvider: CharacterProvider
) {
    private val TAGKEY = CharactersRepository::class.java.simpleName

    fun requestAllCharacters(page: Int): Maybe<List<CharacterDTO>> {
        return getAllCharactersRemote(page)
            .onErrorResumeNext { throwable: Throwable ->
                getAllCharactersLocal(page)
                    .filter { it.isNotEmpty() }
                    .switchIfEmpty(Observable.error(throwable))
            }
            .firstElement()
    }

    /* Local Part */
    private fun getAllCharactersLocal(page: Int): Observable<List<CharacterDTO>> {
        return characterProvider.getAll(page, TAGKEY)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    /* Remote Part */
    private fun getAllCharactersRemote(page: Int): Observable<List<CharacterDTO>> {
        return charactersApi.getAllCharacters(page)
            .cache()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { repos -> repos.results }
            .concatMap { results ->
                characterProvider.add(results, page, TAGKEY)
                    .onErrorReturnItem(results)
            }
    }
}

package com.renatoramos.rickandmorty.data.store.repository.episodes

import com.renatoramos.rickandmorty.data.store.dto.episodes.EpisodeDTO
import com.renatoramos.rickandmorty.data.store.local.paperdb.provider.episodes.EpisodesProvider
import com.renatoramos.rickandmorty.data.store.remote.retrofit.api.episodes.EpisodesApi
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class EpisodesRepository @Inject constructor(
    private val episodesApi: EpisodesApi,
    private val episodesProvider: EpisodesProvider
) {
    private val TAGKEY = EpisodesRepository::class.java.simpleName

    fun requestAllEpisodes(page: Int):  Maybe<List<EpisodeDTO>> {
        return getAllEpisodesRemote(page)
            .onErrorResumeNext { throwable: Throwable ->
                getAllEpisodesLocal(page)
                    .filter { it.isNotEmpty() }
                    .switchIfEmpty(Observable.error(throwable))
            }
            .firstElement()
    }

    //* Local Part *//*
    private fun getAllEpisodesLocal(page: Int): Observable<List<EpisodeDTO>> {
        return episodesProvider.getAll(page, TAGKEY)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    //* Remote Part *//*
    private fun getAllEpisodesRemote(page: Int): Observable<List<EpisodeDTO>> {
        return episodesApi.getAllEpisodes(page)
            .cache()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { repos -> repos.results }
            .concatMap { results ->
                episodesProvider.add(results, page, TAGKEY)
                    .onErrorReturnItem(results)
            }
    }

}

package com.renatoramos.rickandmorty.common.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


abstract class BaseViewModel : ViewModel() {

    private var compositeDisposable = CompositeDisposable()

    /**
     * Contains common cleanup actions needed for all ViewModel, if any.
     * Subclasses may override this.
     */
    fun onStopDisposable() {
        compositeDisposable.clear()
    }

    protected fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    protected fun getCompositeDisposable(): CompositeDisposable {
        if (compositeDisposable.isDisposed) {
            compositeDisposable = CompositeDisposable()
        }
        return compositeDisposable
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}

package com.navin.domain.interactor

import com.navin.domain.executor.PostExecutionThread
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers


/**
 * We add params generic type <n params>
 * All use case classes will need an instance of Post Execution thread
 *
 * Post Execution thread -> provides an abstraction for the scheduler which is to be used
 * when our use-case observable instance is subscribed to
 *
 * Since this class is abstract, kotlin understands that it is implicitly declared as open, hence
 * don't create an instance of this class directly
 *
 * */

abstract class CompletableUseCase<in params> constructor(private val postExecutionThread: PostExecutionThread) {

    private val disposables = CompositeDisposable()


    // An abstract method for any inheriting class to implement
    protected abstract fun buildUseCaseCompletable(params: params? = null): Completable

    // For inheriting class looking to execute any use-cases.
    // Passing in DisposableObserver param type; which allows us to observe for events on our Observables
    // Passing in Params param type if there are any parameters need to be passed to this use-case
    //
    open fun execute(disposableObserver: DisposableCompletableObserver, params: params? = null){

        // Retrieving an instance of this class
        val completable = this.buildUseCaseCompletable(params)
                .subscribeOn(Schedulers.io())
                .observeOn(postExecutionThread.scheduler)


        addDisposable(completable.subscribeWith(disposableObserver))

    }


    fun dispose(){
        disposables.dispose()
    }


    private fun addDisposable(disposable: Disposable){
        disposables.add(disposable)
    }



}
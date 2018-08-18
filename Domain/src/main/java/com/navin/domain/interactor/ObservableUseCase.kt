package com.navin.domain.interactor

import com.navin.domain.executor.PostExecutionThread
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers


/**
 * @link <T> is our Observable data type and for some cases we going to allow parameters so we add params generic type <n params>
 * All use case classes will need an instance of Post Execution thread
 *
 * Post Execution thread -> provides an abstraction for the scheduler which is to be used
 * when our use-case observable instance is subscribed to
 *
 * Since this class is abstract, kotlin understands that it is implicitly declared as open, hence
 * don't create an instance of this class directly
 *
 * */

abstract class ObservableUseCase<T, in params> constructor(
        val postExecutionThread: PostExecutionThread) {

    private val disposables = CompositeDisposable()


    // An abstract method for any inheriting class to implement
    //
    protected abstract fun buildUseCaseObservable(params: params? = null): Observable<T>

    // For inheriting class looking to execute any use-cases.
    // Passing in DisposableObserver param type; which allows us to observe for events on our Observables
    // Passing in Params param type if there are any parameters need to be passed to this use-case
    //
    open fun execute(single: DisposableObserver<T>, params: params? = null){

        // Retrieving an instance of this class
        val observable = this.buildUseCaseObservable(params)
                .subscribeOn(Schedulers.io())
                .observeOn(postExecutionThread.scheduler)
        addDisposable(observable.subscribeWith(single))

    }


    fun dispose(){
        if (!disposables.isDisposed) disposables.dispose()
    }


    private fun addDisposable(disposable: Disposable){
        disposables.add(disposable)
    }



}
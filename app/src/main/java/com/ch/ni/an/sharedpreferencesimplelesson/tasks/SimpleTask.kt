package com.ch.ni.an.sharedpreferencesimplelesson.tasks

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future


private val executorService = Executors.newCachedThreadPool()
private val handler = Handler(Looper.getMainLooper())

class SimpleTask<T>(
    private val callable: Callable<T>
) :Task<T> {

    private val future: Future<*>

    init {
        future = executorService.submit {
            try{

            } catch (e:Throwable){

            }
        }
    }

    override fun onSuccess(callback: Callback<T>): Task<T> {
        TODO("Not yet implemented")
    }

    override fun onError(callback: Callback<Throwable>): Task<T> {
        TODO("Not yet implemented")
    }

    override fun cancel() {
        TODO("Not yet implemented")
    }

    override fun await(): T {
        TODO("Not yet implemented")
    }
}
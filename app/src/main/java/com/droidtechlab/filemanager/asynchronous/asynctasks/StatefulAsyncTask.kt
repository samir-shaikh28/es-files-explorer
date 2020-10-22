package com.droidtechlab.filemanager.asynchronous.asynctasks

/**
 * Interface to define state to Asynctask
 */
interface StatefulAsyncTask<T> {

    /**
     * Set callback to current async task. To be used to attach the context on
     * orientation change of fragment / activity
     * @param t callback
     */
    fun setCallback(t: T)
}
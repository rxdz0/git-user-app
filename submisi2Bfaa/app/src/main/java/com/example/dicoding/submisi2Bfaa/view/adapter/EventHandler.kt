package com.example.dicoding.submisi2Bfaa.view.adapter

open class EventHandler<out T>(private val content: T) {

    @Suppress("CanBePrivate")
    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}
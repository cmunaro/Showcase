package com.example.showcase.base

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow

interface EventLauncher<T> {
    val eventsChannel: Flow<T>

    suspend fun sendEvent(event: T)
}

class EventLauncherImpl<T>: EventLauncher<T> {
    private val _eventsChannel = Channel<T>(capacity = Channel.BUFFERED)
    override val eventsChannel: Flow<T>
        get() = _eventsChannel.consumeAsFlow()

    override suspend fun sendEvent(event: T) {
        _eventsChannel.send(event)
    }
}


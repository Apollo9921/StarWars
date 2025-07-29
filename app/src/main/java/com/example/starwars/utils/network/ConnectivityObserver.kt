package com.example.starwars.utils.network

import kotlinx.coroutines.flow.Flow

/**
 * Defines an observer for network connectivity status.
 *
 * Implementations of this interface are responsible for monitoring the device's
 * network connection and emitting [Status] updates through a [Flow].
 */
interface ConnectivityObserver {

    /**
     * Starts observing network connectivity changes.
     *
     * @return A [Flow] that emits [Status] updates whenever the network
     *         connectivity changes.
     */
    fun observe(): Flow<Status>

    /**
     * Represents the current network connectivity status.
     */
    enum class Status {
        /**
         * Indicates that network connectivity is available.
         */
        Available,

        /**
         * Indicates that network connectivity is unavailable.
         */
        Unavailable
    }
}
package com.example.starwars.utils.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

/**
 * An implementation of [ConnectivityObserver] that uses [ConnectivityManager]
 * to monitor network connectivity changes.
 *
 * This class registers a [ConnectivityManager.NetworkCallback] to receive updates
 * about the network status and emits these updates as a [Flow] of [ConnectivityObserver.Status].
 * It ensures that only distinct status changes are emitted.
 *
 * @param context The application context, used to access the [ConnectivityManager].
 */
class NetworkConnectivityObserver(
    context: Context
): ConnectivityObserver {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    /**
     * Starts observing network connectivity changes.
     *
     * This function uses `callbackFlow` to bridge the callback-based API of
     * [ConnectivityManager.NetworkCallback] with Kotlin Flows.
     * When the flow is collected, it registers a network callback.
     * When the flow is cancelled or completed, it unregisters the callback.
     *
     * [distinctUntilChanged] is used to ensure that only unique status updates
     * are emitted, preventing redundant emissions of the same status.
     *
     * @return A [Flow] that emits [ConnectivityObserver.Status] updates.
     *         It emits [ConnectivityObserver.Status.Available] when a network connection is
     *         available, and [ConnectivityObserver.Status.Unavailable] when the network is
     *         lost or becomes unavailable.
     */
    override fun observe(): Flow<ConnectivityObserver.Status> {
        return callbackFlow {
            val callback = object : ConnectivityManager.NetworkCallback() {
                /**
                 * Called when the framework connects and has declared a new network ready for use.
                 * Emits [ConnectivityObserver.Status.Available].
                 */
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    launch { send(ConnectivityObserver.Status.Available) }
                }

                /**
                 * Called when the framework has a hard loss of the network or when the
                 * graceful failover ends.
                 * Emits [ConnectivityObserver.Status.Unavailable].
                 */
                override fun onLost(network: Network) {
                    super.onLost(network)
                    launch { send(ConnectivityObserver.Status.Unavailable) }
                }

                /**
                 * Called when a network disconnects or when a network that did not satisfy
                 * a request now satisfies it.
                 * This can be triggered for various reasons, including when the network is
                 * no longer the default network, or when it's temporarily unavailable.
                 * For simplicity in this observer, it's treated as [ConnectivityObserver.Status.Unavailable].
                 *
                 * Note: The official documentation for `onUnavailable` states:
                 * "Called when the network is no longer accessible. This may be because
                 * the network disconnected or entered a state that no longer satisfies
                 * the NetworkRequest."
                 * In the context of a default network callback, this typically means no network
                 * is currently fulfilling the default request.
                 */
                override fun onUnavailable() {
                    super.onUnavailable()
                    launch { send(ConnectivityObserver.Status.Unavailable) }
                }
            }

            // Register the callback with the ConnectivityManager to listen for default network changes.
            connectivityManager.registerDefaultNetworkCallback(callback)

            // When the flow is closed (e.g., collector coroutine is cancelled),
            // unregister the callback to prevent leaks.
            awaitClose {
                connectivityManager.unregisterNetworkCallback(callback)
            }
        }.distinctUntilChanged() // Only emit status if it has changed from the previous one.
    }
}
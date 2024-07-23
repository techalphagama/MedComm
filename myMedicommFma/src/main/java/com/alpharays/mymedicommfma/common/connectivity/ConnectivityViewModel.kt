package com.alpharays.mymedicommfma.common.connectivity

import androidx.lifecycle.ViewModel
import com.alpharays.mymedicommfma.common.connectivity.ConnectivityObserver
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ConnectivityViewModel : ViewModel() {
    private val _connectivityStatus = MutableStateFlow(ConnectivityObserver.Status.Lost)
    val connectivityStatus: StateFlow<ConnectivityObserver.Status> = _connectivityStatus.asStateFlow()

    fun updateConnectivityStatus(status: ConnectivityObserver.Status) {
        _connectivityStatus.value = status
    }
}

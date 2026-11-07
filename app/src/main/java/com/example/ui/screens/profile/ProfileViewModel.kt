package com.example.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.DemoTradingRepository
import com.example.domain.model.TraderProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ProfileUiState(
    val notifyNewSignals: Boolean = true,
    val notifyTpHits: Boolean = true,
    val notifySlAlerts: Boolean = true,
    val notifyCommunityAlpha: Boolean = false,
    val isGeminiConnected: Boolean = true,
    val message: String? = null
)

class ProfileViewModel(
    private val demoTradingRepository: DemoTradingRepository
) : ViewModel() {

    val profile: StateFlow<TraderProfile> = demoTradingRepository.traderProfile

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun toggleNotifyNewSignals(value: Boolean) {
        _uiState.value = _uiState.value.copy(notifyNewSignals = value)
    }

    fun toggleNotifyTpHits(value: Boolean) {
        _uiState.value = _uiState.value.copy(notifyTpHits = value)
    }

    fun toggleNotifySlAlerts(value: Boolean) {
        _uiState.value = _uiState.value.copy(notifySlAlerts = value)
    }

    fun toggleNotifyCommunityAlpha(value: Boolean) {
        _uiState.value = _uiState.value.copy(notifyCommunityAlpha = value)
    }

    fun resetDemoAccount() {
        viewModelScope.launch {
            demoTradingRepository.resetAccount()
            _uiState.value = _uiState.value.copy(message = "Demo account reset to $10,000 USDT!")
        }
    }

    fun clearMessage() {
        _uiState.value = _uiState.value.copy(message = null)
    }
}

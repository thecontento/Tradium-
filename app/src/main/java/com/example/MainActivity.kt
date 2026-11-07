package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.data.local.AppDatabase
import com.example.data.repository.AIAnalyzerRepositoryImpl
import com.example.data.repository.ChatRepositoryImpl
import com.example.data.repository.DemoTradingRepositoryImpl
import com.example.data.repository.LeaderboardRepositoryImpl
import com.example.data.repository.SignalRepositoryImpl
import com.example.ui.navigation.AppNavigation
import com.example.ui.screens.analyzer.AIAnalyzerViewModel
import com.example.ui.screens.chat.CommunityChatViewModel
import com.example.ui.screens.leaderboard.LeaderboardViewModel
import com.example.ui.screens.profile.ProfileViewModel
import com.example.ui.screens.signals.SignalsViewModel
import com.example.ui.screens.trading.DemoTradingViewModel
import com.example.ui.theme.CryptoSignalTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize Room Database & Repositories
        val database = AppDatabase.getInstance(applicationContext)
        val signalRepository = SignalRepositoryImpl(database)
        val demoTradingRepository = DemoTradingRepositoryImpl(database)
        val aiAnalyzerRepository = AIAnalyzerRepositoryImpl()
        val chatRepository = ChatRepositoryImpl(database)
        val leaderboardRepository = LeaderboardRepositoryImpl()

        // Initialize ViewModels
        val signalsViewModel = SignalsViewModel(signalRepository, demoTradingRepository)
        val analyzerViewModel = AIAnalyzerViewModel(aiAnalyzerRepository, demoTradingRepository)
        val tradingViewModel = DemoTradingViewModel(demoTradingRepository)
        val chatViewModel = CommunityChatViewModel(chatRepository)
        val leaderboardViewModel = LeaderboardViewModel(leaderboardRepository)
        val profileViewModel = ProfileViewModel(demoTradingRepository)

        setContent {
            CryptoSignalTheme {
                val navController = rememberNavController()
                AppNavigation(
                    navController = navController,
                    signalsViewModel = signalsViewModel,
                    analyzerViewModel = analyzerViewModel,
                    tradingViewModel = tradingViewModel,
                    chatViewModel = chatViewModel,
                    leaderboardViewModel = leaderboardViewModel,
                    profileViewModel = profileViewModel
                )
            }
        }
    }
}

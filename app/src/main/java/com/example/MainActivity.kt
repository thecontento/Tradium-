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
import com.example.data.repository.GeminiChatRepositoryImpl
import com.example.data.repository.LeaderboardRepositoryImpl
import com.example.data.repository.SignalRepositoryImpl
import com.example.ui.navigation.AppNavigation
import com.example.ui.screens.analyzer.AIAnalyzerViewModel
import com.example.ui.screens.chat.CommunityChatViewModel
import com.example.ui.screens.chat.FinanceChatViewModel
import com.example.ui.screens.leaderboard.LeaderboardViewModel
import com.example.ui.screens.profile.ProfileViewModel
import com.example.ui.screens.signals.SignalsViewModel
import com.example.ui.screens.trading.DemoTradingViewModel
import com.example.ui.theme.CryptoSignalTheme
import com.example.util.configureAppCheck

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // STEP 1: Mandatory App Check configuration with launch intent before any Firebase or UI calls
        configureAppCheck(this, intent)

        enableEdgeToEdge()

        // Initialize Room Database & Repositories
        val database = AppDatabase.getInstance(applicationContext)
        val signalRepository = SignalRepositoryImpl(database)
        val demoTradingRepository = DemoTradingRepositoryImpl(database)
        val aiAnalyzerRepository = AIAnalyzerRepositoryImpl()
        val chatRepository = ChatRepositoryImpl(database)
        val geminiChatRepository = GeminiChatRepositoryImpl()
        val leaderboardRepository = LeaderboardRepositoryImpl()

        // Initialize ViewModels
        val signalsViewModel = SignalsViewModel(signalRepository, demoTradingRepository)
        val analyzerViewModel = AIAnalyzerViewModel(aiAnalyzerRepository, demoTradingRepository)
        val financeChatViewModel = FinanceChatViewModel(geminiChatRepository)
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
                    financeChatViewModel = financeChatViewModel,
                    tradingViewModel = tradingViewModel,
                    chatViewModel = chatViewModel,
                    leaderboardViewModel = leaderboardViewModel,
                    profileViewModel = profileViewModel
                )
            }
        }
    }
}

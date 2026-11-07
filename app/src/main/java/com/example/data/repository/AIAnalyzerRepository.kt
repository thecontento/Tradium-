package com.example.data.repository

import com.example.domain.model.AIAnalysisResult
import com.example.domain.model.CandleData
import com.example.domain.model.TechnicalIndicatorResult
import kotlinx.coroutines.delay
import kotlin.math.sin
import kotlin.random.Random

interface AIAnalyzerRepository {
    suspend fun getCandleData(pair: String, timeframe: String): List<CandleData>
    suspend fun runAiAnalysis(pair: String, timeframe: String, strategy: String): AIAnalysisResult
}

class AIAnalyzerRepositoryImpl : AIAnalyzerRepository {

    override suspend fun getCandleData(pair: String, timeframe: String): List<CandleData> {
        val basePrice = when (pair) {
            "BTC/USDT" -> 96500.0f
            "ETH/USDT" -> 2780.0f
            "SOL/USDT" -> 195.0f
            "NEAR/USDT" -> 6.85f
            "AVAX/USDT" -> 34.20f
            "LINK/USDT" -> 19.10f
            "XRP/USDT" -> 2.45f
            else -> 100.0f
        }

        val volatility = basePrice * 0.015f
        val count = 40
        val candles = mutableListOf<CandleData>()
        var currentPrice = basePrice * 0.94f
        val now = System.currentTimeMillis()
        val intervalMs = when (timeframe) {
            "15m" -> 15 * 60 * 1000L
            "1h" -> 60 * 60 * 1000L
            "4h" -> 4 * 60 * 60 * 1000L
            else -> 24 * 60 * 60 * 1000L
        }

        for (i in 0 until count) {
            val stepTrend = sin(i.toDouble() / 5.0).toFloat() * (volatility * 0.6f)
            val randomNoise = (Random.nextFloat() - 0.45f) * volatility
            val open = currentPrice
            val close = open + stepTrend + randomNoise
            val high = maxOf(open, close) + Random.nextFloat() * (volatility * 0.5f)
            val low = minOf(open, close) - Random.nextFloat() * (volatility * 0.5f)
            val volume = 50f + Random.nextFloat() * 150f

            candles.add(
                CandleData(
                    timestamp = now - (count - i) * intervalMs,
                    open = open,
                    high = high,
                    low = low,
                    close = close,
                    volume = volume
                )
            )
            currentPrice = close
        }

        return candles
    }

    override suspend fun runAiAnalysis(pair: String, timeframe: String, strategy: String): AIAnalysisResult {
        // Simulate real AI computation delay
        delay(1200)

        val basePrice = when (pair) {
            "BTC/USDT" -> 96500.0
            "ETH/USDT" -> 2780.0
            "SOL/USDT" -> 195.0
            "NEAR/USDT" -> 6.85
            "AVAX/USDT" -> 34.20
            "LINK/USDT" -> 19.10
            "XRP/USDT" -> 2.45
            else -> 100.0
        }

        val support = basePrice * 0.965
        val resistance = basePrice * 1.042
        val pivot = basePrice * 1.005
        val tp1 = basePrice * 1.035
        val tp2 = basePrice * 1.072
        val sl = basePrice * 0.978

        val indicators = listOf(
            TechnicalIndicatorResult("RSI (14-period)", "62.4", "BULLISH", 78f),
            TechnicalIndicatorResult("MACD (12, 26, 9)", "+148.2 (Expanding)", "STRONG BULLISH", 88f),
            TechnicalIndicatorResult("EMA 50 / 200 Cross", "Golden Cross Active", "BULLISH", 92f),
            TechnicalIndicatorResult("Bollinger Bands %B", "0.74 (Upper Expansion)", "BULLISH", 75f),
            TechnicalIndicatorResult("Order Flow Delta", "+$42.8M Aggressive Buys", "STRONG BULLISH", 95f),
            TechnicalIndicatorResult("Open Interest Trend", "+14.2% (New Capital Inflow)", "BULLISH", 82f)
        )

        return AIAnalysisResult(
            pair = pair,
            timeframe = timeframe,
            strategyName = strategy,
            bias = "STRONG BULLISH",
            confidence = 94,
            keySupport = (support * 100).toLong() / 100.0,
            keyResistance = (resistance * 100).toLong() / 100.0,
            pivotPoint = (pivot * 100).toLong() / 100.0,
            summary = "AI Model detected high-probability liquidity absorption right above key demand zone. Bullish continuation pattern confirmed by multi-timeframe EMA alignment and positive CVD delta.",
            patternDetected = "Ascending Triangle Breakout + Bullish FVG Retest",
            recommendedEntry = (basePrice * 100).toLong() / 100.0,
            recommendedTp1 = (tp1 * 100).toLong() / 100.0,
            recommendedTp2 = (tp2 * 100).toLong() / 100.0,
            recommendedSl = (sl * 100).toLong() / 100.0,
            indicators = indicators,
            riskReward = "1 : 3.25"
        )
    }
}

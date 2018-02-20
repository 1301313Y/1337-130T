package com.leetbot.api

import com.leetbot.api.data.TimePeriod
import com.leetbot.api.wrapper.Balance
import com.leetbot.api.wrapper.CandleStick
import com.leetbot.api.wrapper.Trade
import com.leetbot.api.wrapper.TradingPair
import java.math.BigDecimal

/**
 * ${FILE_NAME}
 *
 * @author Notorious
 * @since 2/19/2018
 * @version 1.0.0
 */
interface API {

    fun name(): String

    fun balances(): List<Balance>

    fun balance(name: String): Balance

    fun currentPrice(pair: TradingPair): BigDecimal

    fun candlesticks(pair: TradingPair, period: TimePeriod, start: Long = -1, end: Long = -1, limit: Int = 500): List<CandleStick>

    fun aggregatedTrades(pair: TradingPair, fromID: Long = -1L, start: Long = -1, end: Long = -1, limit: Int = 500): List<Trade>
}
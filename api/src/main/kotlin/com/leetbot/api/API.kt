package com.leetbot.api

import com.leetbot.api.data.TimePeriod
import com.leetbot.api.wrapper.Balance
import com.leetbot.api.wrapper.CandleStick
import com.leetbot.api.wrapper.TradingPair

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

    fun getBalance(name: String): Balance

    fun candlesticks(pair: TradingPair, period: TimePeriod): List<CandleStick>
}
package com.leetbot.api.impl.binance

import com.leetbot.api.data.TimePeriod
import com.leetbot.api.impl.AbstractAPI
import com.leetbot.api.wrapper.CandleStick
import com.leetbot.api.wrapper.TradingPair
import java.util.LinkedList
import com.leetbot.api.impl.binance.http.BinanceRequest
import com.leetbot.api.impl.binance.wrapper.BinanceCandleStick
import com.leetbot.api.wrapper.Balance
import org.json.simple.JSONArray
import org.json.simple.JSONObject


/**
 * ${FILE_NAME}
 *
 * @author Notorious
 * @version 1.0.0
 * @since 2/19/2018
 */
class BinanceAPI(api: String, secret: String): AbstractAPI(api, secret) {

    companion object {
        const val NAME = "Binance"
        const val BASE_API_URL = "https://www.binance.com/api/"
        const val BASE_WAPI_URL = "https://www.binance.com/wapi/"
        const val BASE_API_V1_URL = BASE_API_URL.plus("v1/")
        const val BASE_API_V3_URL = BASE_API_URL.plus("v3/")
        const val BASE_WAPI_V3_URL = BASE_WAPI_URL.plus("v3/")
        const val WEB_SOCKET_BASE_URL = "wss://stream.binance.com:9443/ws/"
    }

    override fun name(): String {
        return NAME
    }

    override fun candlesticks(pair: TradingPair, period: TimePeriod): List<CandleStick> {
        val u = BASE_API_V1_URL
                .plus("klines?symbol=").plus(pair.toString())
                .plus("&interval=").plus(period.id)
                .plus("&limit=").plus(500)
        return BinanceRequest(u).open().read().toJsonArray().mapTo(LinkedList()) { BinanceCandleStick(it as JSONArray) }
    }

    private fun account(): JSONObject {
        return BinanceRequest(BASE_API_V3_URL + "account")
                .sign(api, secret, null).read().toJsonObject()
    }


    override fun balances(): List<Balance> {
        return (account()["balances"] as JSONArray).map { it as JSONObject }.map {
            val asset = it["asset"] as String
            val available = (it["free"] as String).toBigDecimal()
            val frozen = (it["locked"] as String).toBigDecimal()
            val total = available + frozen
            Balance(asset, total, available, frozen)
        }.toList()
    }
}

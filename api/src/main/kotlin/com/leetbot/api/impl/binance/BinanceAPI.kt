package com.leetbot.api.impl.binance

import com.leetbot.api.data.TimePeriod
import com.leetbot.api.impl.AbstractAPI
import com.leetbot.api.wrapper.CandleStick
import com.leetbot.api.wrapper.TradingPair
import java.util.LinkedList
import com.leetbot.api.impl.binance.http.BinanceRequest
import com.leetbot.api.impl.binance.wrapper.BinanceCandleStick
import com.leetbot.api.wrapper.Balance
import com.leetbot.api.wrapper.Trade
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import java.math.BigDecimal


/**
 * ${FILE_NAME}
 *
 * @author 1301313Y
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

    override fun candlesticks(pair: TradingPair, period: TimePeriod, start: Long, end: Long, limit: Int): List<CandleStick> {
        val u = BASE_API_V1_URL
                .plus("klines")
                .plus("?symbol=").plus(pair.toString())
                .plus("&interval=").plus(period.id)
                .plus(if (start > 0) "&startTime=%d".format(start) else "")
                .plus(if (end > 0) "&endTime=%d".format(end) else "")
                .plus(if (limit in 1..500) "&limit=%d".format(limit) else "")
        return BinanceRequest(u).open().read().toJsonArray().mapTo(LinkedList()) { BinanceCandleStick(it as JSONArray) }
    }

    private fun account(): JSONObject {
        return BinanceRequest(BASE_API_V3_URL + "account")
                .sign(api, secret, null).read().toJsonObject()
    }

    override fun currentPrice(pair: TradingPair): BigDecimal {
        val u = BASE_API_V3_URL
                .plus("ticker/")
                .plus("price")
                .plus("?symbol=").plus(pair.toString())
        return BigDecimal(BinanceRequest(u).open().read().toJsonObject()["price"] as String)
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

    override fun aggregatedTrades(pair: TradingPair, fromID: Long, start: Long, end: Long, limit: Int): List<Trade> {
        val u = BASE_API_V1_URL
                .plus("aggTrades")
                .plus("?symbol=").plus(pair.toString())
                .plus(if (fromID > 0) "&fromId=%d".format(fromID) else "")
                .plus(if (start > 0) "&startTime=%d".format(start) else "")
                .plus(if (end > 0) "&endTime=%d".format(end) else "")
                .plus(if (limit in 1..500) "&limit=%d".format(limit) else "")
        return BinanceRequest(u).open().read().toJsonArray().map { it as JSONObject }.map {
            val id = (it["a"] as Long)
            val price = (it["p"] as String).toBigDecimal()
            val quantity = (it["q"] as String).toBigDecimal()
            val timestamp = (it["T"] as Long)
            val maker = (it["m"] as Boolean)
            val best = (it["M"] as Boolean)
            Trade(id, price, quantity, timestamp, maker, best)
        }.toList()
    }
}

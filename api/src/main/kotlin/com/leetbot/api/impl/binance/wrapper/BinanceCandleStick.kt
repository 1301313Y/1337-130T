package com.leetbot.api.impl.binance.wrapper

import com.leetbot.api.wrapper.CandleStick
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
class BinanceCandleStick(private val json: JSONArray): CandleStick {
    /*
        1499040000000,      // Open time 0
    "0.01634790",       // Open 1
    "0.80000000",       // High 2
    "0.01575800",       // Low 3
    "0.01577100",       // Close4
    "148976.11427815",  // Volume5
    1499644799999,      // Close time6
    "2434.19055334",    // Quote asset volume7
    308,                // Number of trades8
    "1756.87402397",    // Taker buy base asset volume9
    "28.46694368",      // Taker buy quote asset volume10
    "17928899.62484339" // Ignore
     */
    override fun openTimestamp(): Long {
        return json[0].toString().toLong()
    }

    override fun closeTimestamp(): Long {
        return json[6].toString().toLong()
    }

    override fun open(): BigDecimal {
        return json[1].toString().toBigDecimal()
    }

    override fun close(): BigDecimal {
        return json[4].toString().toBigDecimal()
    }

    override fun low(): BigDecimal {
        return json[3].toString().toBigDecimal()
    }

    override fun high(): BigDecimal {
        return json[2].toString().toBigDecimal()
    }

    override fun volume(): BigDecimal {
        return json[5].toString().toBigDecimal()
    }

    override fun trades(): Long {
        return json[8].toString().toLong()
    }
}

package com.leetbot.api.wrapper

import org.json.simple.JSONObject
import java.math.BigDecimal

/**
 * ${FILE_NAME}
 *
 * @author 1301313Y
 * @since 2/19/2018
 * @version 1.0.0
 */
class Trade(val id: Long, val price: BigDecimal, val quantity: BigDecimal,
            val timestamp: Long, val maker: Boolean, val bestPrice: Boolean) {

    val json: JSONObject = JSONObject()

    init {
        json["timestamp"] = timestamp
        json["id"] = id
        json["price"] = "%.8f".format(price)
        json["quantity"] = "%.8f".format(quantity)
        json["maker"] = maker
        json["bestPrice"] = bestPrice
    }

    override fun toString(): String {
        return json.toString()
    }
}
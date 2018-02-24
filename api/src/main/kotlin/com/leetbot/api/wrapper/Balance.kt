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
class Balance(val name: String, val total: BigDecimal, val available: BigDecimal, val frozen: BigDecimal) {

    val json: JSONObject = JSONObject()

    init {
        json["name"] = name
        json["total"] = "%.8f".format(total)
        json["available"] = "%.8f".format(available)
        json["frozen"] = "%.8f".format(frozen)
    }

    override fun toString(): String {
        return json.toString()
    }
}
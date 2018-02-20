package com.leetbot.commons.exchange

import com.leetbot.commons.config.Configurations
import com.leetbot.commons.security.AES

/**
 * ${FILE_NAME}
 *
 * @author Notorious
 * @since 2/19/2018
 * @version 1.0.0
 */
class Exchange(val name: String, val url: String, val imageURL: String, val description: String) {

    companion object {
        val binance = Exchange("Binance", "https://www.binance.com/",
                "https://i.imgur.com/acTy24T.png",
                "Binance is the fastest growing" +
                " exchange for trading crypto-currencies. In the short duration of 5 months, it reached the 10th " +
                "position among the top volume crypto-currency exchanges.")
        val kraken = Exchange("Kraken", "https://www.kraken.com/",
                "https://lever-client-logos.s3.amazonaws.com/741f7d55-0312-4036-bd47-ce74d90a2485-1489794864111.png",
                "Kraken is a US-based " +
                "crypto-currency exchange operating in Canada, the EU, Japan, and the US, and \"the world's largest" +
                " bitcoin exchange in euro volume and liquidity\". In June 2016, Kraken added Ethereum dark pool" +
                " trading for large bitcoin buyers")

        fun getExchangeForName(name: String): Exchange {
            if (name.toLowerCase() == "binance") {
                return binance
            } else if (name.toLowerCase() == "kraken") {
                return kraken
            } else{
                println("Invalid exchange name was given! Reverting to Binance.")
                return binance
            }
        }
    }

    var api: String
    var secret: String

    init {
        var exchange = Configurations.getExchange(name)
        if(exchange == null) {
            api = ""
            secret = ""
            Configurations.updateExchange(this)
        } else {
            var apiKey = exchange["api"] as String
            var secretKey = exchange["secret"] as String
            if (apiKey.isNotEmpty()) {
                apiKey = AES.decrypt(apiKey)
            }
            if (secretKey.isNotEmpty()) {
                secretKey = AES.decrypt(secretKey)
            }
            api = apiKey
            secret = secretKey
        }
    }

    override fun toString(): String {
        return name
    }

}
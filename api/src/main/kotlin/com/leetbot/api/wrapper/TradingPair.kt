package com.leetbot.api.wrapper

/**
 * ${FILE_NAME}
 *
 * @author Notorious
 * @since 2/19/2018
 * @version 1.0.0
 */
class TradingPair(val asset: String, val currency: String, var minimal:Boolean = true) {

    override fun toString(): String {
        return if(minimal) asset.plus(currency) else asset.plus('_').plus(currency)
    }
}
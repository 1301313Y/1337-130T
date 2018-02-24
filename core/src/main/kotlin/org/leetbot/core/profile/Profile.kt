package org.leetbot.core.profile

import com.leetbot.api.wrapper.TradingPair

/**
 * ${FILE_NAME}
 *
 * @author 1301313Y
 * @since 2/24/2018
 * @version 1.0.0
 */
class Profile(
        var name: String,
        var pair: TradingPair,
        var maxTradeQuantity: Int,
        var stopLossPercent: Int = 0,
        val indicators: Array<Any> //TODO Set to indicator
)

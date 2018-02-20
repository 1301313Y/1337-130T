package com.leetbot.api.wrapper

import java.math.BigDecimal

/**
 * ${FILE_NAME}
 *
 * @author Notorious
 * @since 2/19/2018
 * @version 1.0.0
 */
interface CandleStick {

    fun openTimestamp(): Long

    fun closeTimestamp(): Long

    fun open(): BigDecimal

    fun low(): BigDecimal

    fun high(): BigDecimal

    fun close(): BigDecimal

    fun volume(): BigDecimal

    fun trades(): Long

}
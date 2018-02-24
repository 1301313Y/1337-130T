package com.leetbot.api.data

/**
 * ${FILE_NAME}
 *
 * @author 1301313Y
 * @since 2/19/2018
 * @version 1.0.0
 */
enum class TimePeriod(val id: String, val minutes: Long) {

    ONE_MINUTE("1m", 1),
    FIVE_MINUTE("5m",  5),
    TEN_MINUTE("10m", 10);

}
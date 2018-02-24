package com.leetbot.commons.utils

import org.apache.log4j.Level
import org.apache.log4j.Logger

/**
 * ${FILE_NAME}
 *
 * @author 1301313Y
 * @version 1.0.0
 * @since 2/5/2018
 */

object BotLogger {

    private val logger = Logger.getRootLogger()

    fun info(message: Any) {
        logger.info(message)
    }
    fun debug(message: Any) {
        logger.debug(message)
    }
    fun warn(message: Any) {
        logger.warn(message)
    }
    fun error(message: Any, thrown: Throwable) {
        logger.error(message, thrown)
    }
    fun setLevel(level: Level) {
        logger.level = level
    }
}
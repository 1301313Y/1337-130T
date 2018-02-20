package com.leetbot

import com.leetbot.config.Configurations
import org.apache.log4j.Level
import org.apache.log4j.Logger

/**
 * ${FILE_NAME}
 *
 * @author Notorious
 * @version 1.0.0
 * @since 2/5/2018
 */

object BotLogger {

    private val logger = Logger.getRootLogger()

    fun info(message: Any) {
        logger.info(message)
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

fun main(args: Array<String>) {
    println("-".repeat(80))
    println("Welcome to 1337 130T v1.0.0!")
    println("Author: @1301313Y")
    if(!Configurations.hasAcceptedToConditions()) {
        println("Before we continue, please read disclaimer below:")
        println("Warning using this application at your own risk! You can lose money! You take full responsibilities of" +
                "any actions taken by this bot!")
        println("Do you agree? (y/n)")
        val input = readLine()
        if(input?.get(0)?.toLowerCase() == 'y') {
            println("\"If you take no risks, you will suffer no defeats. But if you take no risks," +
                    " you win no victories.\"\n\t-Richard M. Nixon")
            Configurations.setAcceptedToConditions(true)
        }
    }
    if(Configurations.hasAcceptedToConditions()) {

    }
}

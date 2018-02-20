package com.leetbot.bot

import com.leetbot.api.data.TimePeriod
import com.leetbot.api.impl.binance.BinanceAPI
import com.leetbot.api.wrapper.TradingPair
import com.leetbot.commons.config.Configurations
import com.leetbot.commons.exchange.Exchange
import javafx.application.Application
import org.leetbot.gui.BotApplication

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
       // Application.launch(BotApplication::class.java, *args)
    }
    val exchange = Exchange.binance
    val api = BinanceAPI(exchange.api, exchange.secret)
    println(api.balances())
}

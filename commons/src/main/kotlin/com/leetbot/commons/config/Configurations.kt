package com.leetbot.commons.config

import com.leetbot.commons.exchange.Exchange
import com.leetbot.commons.security.AES
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import java.io.File
import java.io.FileReader
import org.json.simple.parser.JSONParser
import javax.swing.JFileChooser
import java.io.FileWriter


/**
 * ${FILE_NAME}
 *
 * @author 1301313Y
 * @since 2/19/2018
 * @version 1.0.0
 */
object Configurations {

    private val directory = File(JFileChooser().fileSystemView.defaultDirectory, "1337130T")
    private val file = File(directory, "config.json")
    private val configuration = getConfigurationObject()

    private fun getConfigurationObject(): JSONObject {
        val parser = JSONParser()
        if(!directory.exists()) {
            directory.mkdirs()
        }
        if(!file.exists()) {
            val obj = JSONObject()
            obj["accepted_conditions"] = false
            obj["exchange"] = "Binance"
            // try-with-resources statement based on post comment below :)
            save(obj)
        }
        return parser.parse(FileReader(file)) as JSONObject
    }

    fun rootDirectory(): File {
        return directory
    }

    private fun save() {
        save(configuration)
    }

    private fun save(obj: JSONObject) {
        FileWriter(file).use { f ->
            f.write(obj.toJSONString())
        }
    }

    fun updateExchange(exchange: Exchange): JSONObject {
        var stored = getExchange(exchange.name)
        if(stored == null) {
            stored = addExchange(exchange)
        } else{
            stored["api"] = AES.encrypt(exchange.api)
            stored["secret"] = AES.encrypt(exchange.secret)
        }
        save()
        return stored
    }

    fun getExchange(name: String): JSONObject? {
        verifyExchanges()
        val exchanges = configuration["exchanges"] as JSONArray
        return exchanges
                .map { it as JSONObject }.firstOrNull { (it["name"] as String).toLowerCase() == name.toLowerCase() }
    }

    private fun addExchange(exchange: Exchange): JSONObject {
        verifyExchanges()
        val exchanges = configuration["exchanges"] as JSONArray
        val o = JSONObject()
        o["name"] = exchange.name
        o["api"] = AES.encrypt(exchange.api)
        o["secret"] = AES.encrypt(exchange.secret)
        exchanges.add(o)
        return o
    }

    private fun verifyExchanges() {
        if (configuration["exchanges"] == null) {
            configuration["exchanges"] = JSONArray()
            save()
        }
    }

    fun hasAcceptedToConditions(): Boolean {
        return configuration["accepted_conditions"] as Boolean
    }

    fun setAcceptedToConditions(choice: Boolean) {
        configuration["accepted_conditions"] = choice
        save()
    }

}
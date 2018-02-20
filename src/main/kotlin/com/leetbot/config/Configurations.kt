package com.leetbot.config

import org.json.simple.JSONObject
import java.io.File
import java.io.FileReader
import org.json.simple.parser.JSONParser
import javax.swing.JFileChooser
import java.io.FileWriter




/**
 * ${FILE_NAME}
 *
 * @author Notorious
 * @since 2/19/2018
 * @version 1.0.0
 */
object Configurations {

    private val directory = File(JFileChooser().fileSystemView.defaultDirectory, "1337 130T")
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

    private fun save() {
        save(configuration)
    }

    private fun save(obj: JSONObject) {
        FileWriter(file).use { f ->
            f.write(obj.toJSONString())
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
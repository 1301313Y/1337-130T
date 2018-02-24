package org.leetbot.gui

import javafx.stage.Stage
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene


/**
 * ${FILE_NAME}
 *
 * @author 1301313Y
 * @since 2/19/2018
 * @version 1.0.0
 */
class BotApplication : javafx.application.Application() {

    override fun start(stage: Stage?) {
        val root = FXMLLoader.load<Any>(javaClass.getResource("view/application.fxml")) as Parent
        val scene = Scene(root)
        //scene.stylesheets.add(this::class.java.getResource("modena-dark.css").toExternalForm())
        stage!!.title = "1337 130T"
        stage.scene = scene
        stage.show()
    }

}
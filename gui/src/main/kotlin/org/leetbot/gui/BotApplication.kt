package org.leetbot.gui

import javafx.stage.Stage
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene


/**
 * ${FILE_NAME}
 *
 * @author Notorious
 * @since 2/19/2018
 * @version 1.0.0
 */
class BotApplication : javafx.application.Application() {

    override fun start(stage: Stage?) {
        val root = FXMLLoader.load<Any>(javaClass.getResource("view/application.fxml")) as Parent
        val scene = Scene(root, 765.0, 503.0)
        stage!!.title = "1337 130T"
        stage.scene = scene
        stage.show()
    }

}
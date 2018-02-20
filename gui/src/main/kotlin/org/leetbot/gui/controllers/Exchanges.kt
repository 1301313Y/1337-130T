package org.leetbot.gui.controllers

import com.leetbot.commons.config.Configurations
import com.leetbot.commons.exchange.Exchange
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import java.awt.Desktop
import java.net.URL

/**
 * ${FILE_NAME}
 *
 * @author Notorious
 * @since 2/19/2018
 * @version 1.0.0
 */
class Exchanges {

    @FXML
    private lateinit var exchangeChoiceBox: ChoiceBox<Exchange>
    @FXML
    private lateinit var apiTextField: TextField
    @FXML
    private lateinit var secretTextField: TextField
    @FXML
    private lateinit var saveButton: Button
    @FXML
    private lateinit var exchangeImage: ImageView
    @FXML
    private lateinit var exchangeLink: Hyperlink
    @FXML
    private lateinit var exchangeDescription: Label

    @FXML
    fun initialize() {
        exchangeDescription.isWrapText = true
        exchangeChoiceBox.items.addAll(Exchange.binance, Exchange.kraken)
        exchangeChoiceBox.value = Exchange.binance
        exchangeChoiceBox.setOnAction {
            update()
        }
        saveButton.setOnAction {
            val item = exchangeChoiceBox.selectionModel.selectedItem
            item.api = apiTextField.text
            item.secret = secretTextField.text
            Configurations.updateExchange(item)
        }
        exchangeLink.setOnAction {
            val item = exchangeChoiceBox.selectionModel.selectedItem
            Desktop.getDesktop().browse(URL(item.url).toURI())
        }
        update()
    }

    private fun update() {
        val item = exchangeChoiceBox.selectionModel.selectedItem
        apiTextField.text = item.api
        secretTextField.text = item.secret
        exchangeImage.imageProperty().set(Image(item.imageURL))
        exchangeDescription.text = item.description
    }

}
package ru.freestyle.keygen.ui

import javafx.application.Platform
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.geometry.Pos
import javafx.scene.control.TableColumn
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import ru.freestyle.keygen.Main
import ru.freestyle.keygen.github.PasswordContext
import ru.freestyle.keygen.ui.data.PasswordData
import tornadofx.*
import java.lang.Thread.sleep
import kotlin.concurrent.thread
import kotlin.system.exitProcess

class GlobalUI : View("RustKeyGen") {

    init {
        primaryStage.isResizable = false
    }

    companion object {
        private const val MAX_HEIGHT: Double = 280.0
        private const val MAX_WIDTH: Double = 265.0

        private val COLOR_RED = c("EA7C65")

        val keysName = listOf("C", "V", "F", "R", "Q")
        private val passwordDataList: ObservableList<PasswordData> = FXCollections.observableArrayList()

        fun addPasswordData(key: String) {
            passwordDataList.add(PasswordData(key))
        }

        var lastKey = ""
    }

    override val root = form {
        this.setPrefSize(MAX_WIDTH, MAX_HEIGHT)

        this.maxWidth = MAX_WIDTH
        this.maxHeight = MAX_HEIGHT

        vbox {
            this.prefWidth = 150.0
            this.prefHeight = 200.0

            this.alignment = Pos.TOP_LEFT
            this.spacing = 10.0

            textflow {
                text("Меню паролей") {
                    font = Font.font(15.0)

                    style {
                        fontWeight = FontWeight.BOLD
                    }
                }
            }

            hbox {
                this.spacing = 10.0

                togglebutton {
                    tooltip(
                        "◌ Включает или отключает функцию\n" +
                                "автоматического заполнения пароля в игре."
                    ) {
                        font = Font.font(13.0)
                    }

                    font = Font.font(13.0)
                    layoutX = 15.0

                    textProperty().bind(Main.autoKeyBoardProperty.stringBinding {
                        if (Main.autoKeyBoard) "Отключить" else "Включить"
                    })

                    action {
                        Main.autoKeyBoard = !Main.autoKeyBoard
                    }
                }

                val observable = FXCollections.observableArrayList(keysName)

                combobox(values = observable) {
                    tooltip(
                        "Возможный выбор действующих кнопок\n" +
                                " ◌ Используется для авто заполнения пароля в игре."
                    ) {
                        font = Font.font(13.0)
                    }

                    style {
                        font = Font.font(10.0)
                    }

                    this.selectionModel.select(Main.selectedKey)

                    this.valueProperty().addListener { _, _, newValue ->
                        Main.selectedKey = newValue ?: "C"
                    }
                }

                progressindicator {
                    tooltip(
                        "Индикатор которые показывает текущее состояние\n" +
                                "завершенного процента от максимального списка"
                    ) {
                        font = Font.font(13.0)
                    }

                    thread {
                        val total = PasswordContext.totalPasswordKeyGen

                        if (total == 0) {
                            Platform.runLater {
                                progress = 1.0
                            }

                            return@thread
                        }

                        try {
                            while (PasswordContext.passwordKeyGen.isNotEmpty()) {
                                val remaining = PasswordContext.passwordKeyGen.size

                                val progressValue = (total - remaining).toDouble() / total.toDouble()

                                Platform.runLater {
                                    progress = progressValue
                                }

                                sleep(3000)
                            }
                        } catch (ex: Exception) {
                            ex.printStackTrace()
                        }
                    }
                }
            }

            hbox {
                this.spacing = 5.0

                tableview {
                    prefHeight = 100.0

                    val keyColumn = TableColumn<PasswordData, String>("Использованные пароли")
                        .minWidth(225.0)
                        .apply {
                            cellValueFactory = PropertyValueFactory("key")
                        }

                    columns.add(keyColumn)

                    thread {
                        try {
                            while (true) {
                                items.setAll(passwordDataList)

                                sleep(2500)
                            }
                        } catch (ex: Exception) {
                            ex.printStackTrace()
                        }
                    }
                }
            }

            hbox {
                this.spacing = 10.0

                textflow {
                    alignment = Pos.BOTTOM_LEFT

                    val textLastPassword = text("Последний введенный пароль $lastKey") {
                        font = Font.font(13.0)

                        style {
                            fontWeight = FontWeight.BLACK
                        }
                    }

                    thread {
                        while (true) {
                            try {
                                textLastPassword.text = "Последний введенный пароль $lastKey"
                                sleep(1000)
                            } catch (ex: Exception) {
                                ex.printStackTrace()
                            }

                        }
                    }
                }
            }
        }

        hbox {
            this.alignment = Pos.CENTER
            this.spacing = 25.0

            this.translateY = 40.0

            button("Закрыть") {
                this.prefWidth = 100.0
                this.prefHeight = 25.0

                font = Font.font(13.0)

                this.style {
                    backgroundColor += COLOR_RED
                    borderRadius += box(15.px)
                }

                this.action {
                    Platform.exit()
                    exitProcess(0)
                }
            }
        }
    }
}
package ru.freestyle.keygen

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import javafx.application.Application
import javafx.beans.property.SimpleBooleanProperty
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.text.Text
import javafx.stage.Stage
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import ru.freestyle.keygen.extension.border
import ru.freestyle.keygen.github.PasswordContext
import ru.freestyle.keygen.keytab.KeyTab
import ru.freestyle.keygen.ui.GlobalUI
import tornadofx.*
import kotlin.reflect.KClass

class Main : App() {

    init {
        println("Starting Droch Password")
        runBlocking {
            PasswordContext().generatePasswords()
        }

        KeyTab().start()
    }

    companion object {
        private const val debugBox: Boolean = false
        val autoKeyBoardProperty = SimpleBooleanProperty(false)

        var autoKeyBoard: Boolean
            get() = autoKeyBoardProperty.get()
            set(value) = autoKeyBoardProperty.set(value)

        var selectedKey = "C"

        val httpClient = HttpClient(OkHttp) {
            install (ContentNegotiation) {
                json(Json {
                    encodeDefaults = true
                    ignoreUnknownKeys = true
                })
            }
        }
    }

    override val primaryView: KClass<out UIComponent>
        get() = GlobalUI::class

    override fun start(stage: Stage) {
        super.start(stage)

        val iconStream = javaClass.getResourceAsStream("/image/logo.jpg")
        if (iconStream != null) {
            stage.icons.add(Image(iconStream))
        }

        if (debugBox) {
            find<GlobalUI>().apply {
                root.childrenUnmodifiable.forEach { node ->
                    if (node is Label || node is Text || node is HBox || node is VBox) {
                        node.border()
                    }
                }
            }
        }
    }
}


fun main(args: Array<String>) {
    Application.launch(Main::class.java, *args)
}

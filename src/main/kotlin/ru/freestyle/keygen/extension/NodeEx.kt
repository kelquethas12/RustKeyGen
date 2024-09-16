package ru.freestyle.keygen.extension

import javafx.scene.Node
import javafx.scene.paint.Color
import tornadofx.box
import tornadofx.px
import tornadofx.style

fun Node.border() {
    style {
        borderColor += box(Color.RED)
        borderWidth += box(1.px)
    }
}
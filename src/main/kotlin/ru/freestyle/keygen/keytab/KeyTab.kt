package ru.freestyle.keygen.keytab

import com.github.kwhat.jnativehook.GlobalScreen
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener
import ru.freestyle.keygen.Main
import ru.freestyle.keygen.github.PasswordContext
import ru.freestyle.keygen.ui.GlobalUI
import java.awt.Robot
import java.awt.event.KeyEvent
import java.lang.Thread.sleep

class KeyTab : NativeKeyListener {

    private val robot = Robot()

    override fun nativeKeyPressed(e: NativeKeyEvent) {
        if (!Main.autoKeyBoard) return

        val selectedKeyCode = this.getKeyCode(Main.selectedKey)

        when (e.keyCode) {
            selectedKeyCode -> {
                val password = PasswordContext.passwordKeyGen.first()
                for (c in password) {
                    val keyType = this.getKeyType(c.toString())
                    robot.keyPress(keyType)
                    robot.keyRelease(keyType)

                    sleep(100)
                }

                println("Input password $password")
                GlobalUI.lastKey = password
                PasswordContext.passwordKeyGen.removeIf { it == password }
                GlobalUI.addPasswordData(password)

                if (password.toIntOrNull()?.rem(10) == 0) {
                    println("Passwords left " + PasswordContext.passwordKeyGen.size)
                }
            }

            else -> {
            }
        }
    }

    override fun nativeKeyReleased(e: NativeKeyEvent) {
    }

    override fun nativeKeyTyped(e: NativeKeyEvent) {

    }

    fun start() {
        GlobalScreen.registerNativeHook()
        GlobalScreen.addNativeKeyListener(this)
        println("Press C")
    }

    private fun getKeyCode(key: String): Int =
        when (key) {
            "C" -> NativeKeyEvent.VC_C
            "V" -> NativeKeyEvent.VC_V
            "F" -> NativeKeyEvent.VC_F
            "R" -> NativeKeyEvent.VC_R
            "Q" -> NativeKeyEvent.VC_Q
            else -> NativeKeyEvent.VC_UNDEFINED
        }

    private fun getKeyType(char: String): Int =
        when (char) {
            "1" -> KeyEvent.VK_1
            "2" -> KeyEvent.VK_2
            "3" -> KeyEvent.VK_3
            "4" -> KeyEvent.VK_4
            "5" -> KeyEvent.VK_5
            "6" -> KeyEvent.VK_6
            "7" -> KeyEvent.VK_7
            "8" -> KeyEvent.VK_8
            "9" -> KeyEvent.VK_9
            "0" -> KeyEvent.VK_0
            else -> {
                KeyEvent.VK_0
            }
        }
}



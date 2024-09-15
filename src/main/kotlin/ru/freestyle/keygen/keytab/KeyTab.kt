package ru.freestyle.keygen.keytab

import com.github.kwhat.jnativehook.GlobalScreen
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener
import ru.freestyle.keygen.github.passwordKeyGen
import java.awt.Robot
import java.awt.event.KeyEvent
import java.lang.Thread.sleep

class KeyTab : NativeKeyListener {
    private val robot = Robot()

    override fun nativeKeyPressed(e: NativeKeyEvent) {
        when (e.keyCode) {
            NativeKeyEvent.VC_C -> {
                val password = passwordKeyGen.first()
                for (c in password) {
                    val keyType = getKeyType(c.toString())
                    robot.keyPress(keyType)
                    robot.keyRelease(keyType)
                    sleep(100)
                }
                println("Input password $password")
                passwordKeyGen.removeIf { it == password }
                if (password.toIntOrNull()?.rem(10) == 0) {
                    println("Passwords left " + passwordKeyGen.size)
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
}
private fun getKeyType(char: String): Int {
    return when(char) {
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

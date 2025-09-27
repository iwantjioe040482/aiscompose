package com.arcadia.aiscompose.Repository

import com.arcadia.aiscompose.Model.Message
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ChatRepository {
    private val listeners = mutableListOf<(Message) -> Unit>()

    fun sendMessage(msg: Message) {
        // Simulate network latency then broadcast incoming reply
        listeners.forEach { it(msg) }

        // Simulate reply after 700ms
        CoroutineScope(Dispatchers.Default).launch {
            delay(700)
            val reply = Message(
                text = "Reply to: ${msg.text}",
                senderId = "other",
                isMine = false
            )
            listeners.forEach { it(reply) }
        }
    }

    fun addListener(cb: (Message) -> Unit) {
        listeners += cb
    }

    fun removeListener(cb: (Message) -> Unit) {
        listeners -= cb
    }
}
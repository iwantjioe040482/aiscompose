package com.arcadia.aiscompose.ViewModel

import com.arcadia.aiscompose.Repository.ChatRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.mutableStateListOf
import com.arcadia.aiscompose.Model.Message
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

class ChatViewModel(
    private val repo: ChatRepository = ChatRepository()
) : ViewModel() {
    private val _messages = mutableStateListOf<Message>()
    val messages: SnapshotStateList<Message> = _messages

    init {
        // register listener
        repo.addListener { m ->
            viewModelScope.launch(Dispatchers.Main) {
                _messages.add(m)
            }
        }
    }

    fun send(text: String, myId: String = "me") {
        if (text.isBlank()) return
        val msg = Message(text = text.trim(), senderId = myId, isMine = true)
        _messages.add(msg)
        repo.sendMessage(msg)
    }

    override fun onCleared() {
        super.onCleared()
        // remove listeners if needed
    }
}
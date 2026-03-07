package com.emarc.chat.presentation.chat_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emarc.core.domain.auth.SessionStorage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ChatListViewModel(
    private val sessionStorage: SessionStorage
) : ViewModel() {

}
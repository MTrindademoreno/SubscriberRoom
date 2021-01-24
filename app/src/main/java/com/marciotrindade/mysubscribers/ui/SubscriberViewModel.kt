package com.marciotrindade.mysubscribers.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marciotrindade.mysubscribes.R
import com.marciotrindade.mysubscribers.repository.SubscribeRepository
import kotlinx.coroutines.launch

//chama a repository interface
class SubscriberViewModel(private val repository: SubscribeRepository) : ViewModel() {

    private val _subscriberStateEventData = MutableLiveData<SubscriberState>()
    val subscriberStateEventData: LiveData<SubscriberState>
        get() = _subscriberStateEventData

    private val _messageStateEventData = MutableLiveData<Int>()
    val messageEventData: LiveData<Int>
        get() = _messageStateEventData

    fun addSubscriber(name: String, email: String) = viewModelScope.launch {
        try {
            val id = repository.insertSubscriber(name, email)
            if (id > 0) {//se o retorno for > 0 foi realizada a inserção
                _subscriberStateEventData.value = SubscriberState.Inserted
                _messageStateEventData.value= R.string.subscriber_inserted_successfully
            }

        } catch (ex: Exception) {
            Log.e(TAG, ex.toString())
        }
    }

    sealed class SubscriberState {
        object Inserted : SubscriberState()
    }

    companion object {
        private val TAG = SubscriberViewModel::class.java.simpleName
    }
}
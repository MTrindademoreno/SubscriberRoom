package com.marciotrindade.mysubscribers.repository

import androidx.lifecycle.LiveData
import com.marciotrindade.mysubscribers.data.db.entity.SubscriberEntity

interface SubscribeRepository {

    suspend fun insertSubscriber(name:String,email:String):Long

    suspend fun updateSubscriber(id:Long, name: String, email: String)

    suspend fun deleteSubscriber(id: Long)

    suspend fun deleteAllSubscribers()

    suspend fun getAllSubscribers():LiveData<List<SubscriberEntity>>
}
 package com.marciotrindade.mysubscribers.repository

import androidx.lifecycle.LiveData
import com.marciotrindade.mysubscribers.data.db.dao.SubscriberDao
import com.marciotrindade.mysubscribers.data.db.entity.SubscriberEntity

class DatabaseDataSource(
    private val subscriberDao: SubscriberDao
): SubscribeRepository {
    override suspend fun insertSubscriber(name: String, email: String): Long {

        val subscriber = SubscriberEntity(
            name = name,
            email = email
        )
       return subscriberDao.insert(subscriber)
    }

    override suspend fun updateSubscriber(id: Long, name: String, email: String) {
        val subscriber = SubscriberEntity(
            id = id,
            name = name,
            email = email
        )
        subscriberDao.update(subscriber)
    }

    override suspend fun deleteSubscriber(id: Long) {
       subscriberDao.delete(id)
    }

    override suspend fun deleteAllSubscribers() {
        subscriberDao.deleteAll()
    }

    override suspend fun getAllSubscribers(): LiveData<List<SubscriberEntity>> {
       return subscriberDao.getAll()
    }
}
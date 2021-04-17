package com.example.myapplication.room

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.myapplication.models.DataModels
import kotlinx.coroutines.flow.Flow

class ContactRepository(private val contactDao: ContactDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allContacts: Flow<List<DataModels.Contact>> = contactDao.getContacts()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(contact: DataModels.Contact) {
        contactDao.insert(contact)
    }
}
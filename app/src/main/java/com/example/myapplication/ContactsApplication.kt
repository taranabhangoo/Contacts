package com.example.myapplication

import android.app.Application
import com.example.myapplication.room.ContactRepository
import com.example.myapplication.room.ContactRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ContactsApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { ContactRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { ContactRepository(database.contactDao()) }
}
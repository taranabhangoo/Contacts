package com.example.myapplication.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.models.DataModels.Contact
import kotlinx.coroutines.flow.Flow


@Dao
interface ContactDao {

    @Query("SELECT * FROM contacts_table")
    fun getContacts(): Flow<List<Contact>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(contact: Contact)

    @Query("DELETE FROM contacts_table")
    suspend fun deleteAll()
}
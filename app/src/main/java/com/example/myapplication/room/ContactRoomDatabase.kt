package com.example.myapplication.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myapplication.models.DataModels
import com.example.myapplication.utils.ParserUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.InputStream


@Database(entities = [DataModels.Contact::class], version = 1, exportSchema = false)
public abstract class ContactRoomDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: ContactRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): ContactRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContactRoomDatabase::class.java,
                    "contact_database"
                )
                    .fallbackToDestructiveMigration()
                .addCallback(ContactsDatabaseCallback(scope, context)).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

    private class ContactsDatabaseCallback(
        private val scope: CoroutineScope,
        private val context: Context
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.contactDao())
                }
            }
        }

        suspend fun populateDatabase(contactDao: ContactDao) {
            // Delete all content here.
            contactDao.deleteAll()

            // Add sample words.
            val json = loadJSONFromAsset(context)
            json?.let {
                val contacts = ParserUtil.parseContacts(it)
                contacts.forEach { contact -> contactDao.insert(contact) }
            }
        }

        private fun loadJSONFromAsset(context: Context): String? {
            val json : String
            try {
                val stream: InputStream = context.assets.open("assets.json")
                val size: Int = stream.available()
                // Read the entire asset into a local byte buffer.
                val buffer = ByteArray(size)
                stream.read(buffer)
                stream.close()
                json = String(buffer)
            } catch (e: IOException) {
                e.printStackTrace()
                return null
            }
            return json
        }
    }
}


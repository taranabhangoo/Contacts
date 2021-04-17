package com.example.myapplication.utils

import android.util.Log
import com.example.myapplication.models.DataModels.Contact
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

object ParserUtil {

    fun parseContacts(response: String): ArrayList<Contact> {
        val gson = Gson()
        val type = object : TypeToken<ArrayList<Contact>>() {}.type
        val contactsList: ArrayList<Contact> = gson.fromJson(response, type)
        Log.v(
            ParserUtil::class.java.simpleName,
            contactsList.toString()
        )
        return contactsList
    }
}
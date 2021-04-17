package com.example.myapplication.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

class DataModels {

    data class Phone(var label : String? = null, var value : String? = null)
    data class Email(var label: String? = null, var value: String? = null)
    data class Name(var first: String? = null, var second: String? = null)

    @Entity(tableName = "contacts_table")
    class Contact(@PrimaryKey(autoGenerate = true) val id: Int?,
                  @ColumnInfo(name = "name") var name: Name?,
                  @SerializedName("phone") @ColumnInfo(name = "phones") var phones: ArrayList<Phone>? = ArrayList(),
                  @SerializedName("email") @ColumnInfo(name = "emails") var emails: ArrayList<Email>? = ArrayList())
}


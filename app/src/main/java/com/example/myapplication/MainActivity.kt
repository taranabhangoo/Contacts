package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapters.ContactsAdapter
import com.example.myapplication.models.DataModels
import com.example.myapplication.utils.ParserUtil
import java.io.IOException
import java.io.InputStream


class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ContactsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get list of contacts
        setUpRecyclerView()
    }



    private fun setUpRecyclerView() {

        val json = loadJSONFromAsset()
        var contacts = ArrayList<DataModels.Contact>()
        json?.let {
            contacts = ParserUtil.parseContacts(it)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL))
        adapter = ContactsAdapter(contacts)
        recyclerView.adapter = adapter


    }

    private fun loadJSONFromAsset(): String? {
        val json : String
        try {
            val stream: InputStream = assets.open("assets.json")
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
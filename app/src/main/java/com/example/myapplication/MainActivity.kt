package com.example.myapplication

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapters.ContactsAdapter
import com.example.myapplication.models.ContactViewModel
import com.example.myapplication.models.ContactViewModelFactory
import com.example.myapplication.models.DataModels
import com.example.myapplication.utils.ParserUtil
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.io.InputStream


class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ContactsAdapter

    private val contactViewModel: ContactViewModel by viewModels {
        ContactViewModelFactory((application as ContactsApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get list of contacts
        setUpRecyclerView()
    }



    private fun setUpRecyclerView() {

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL))
        adapter = ContactsAdapter()
        recyclerView.adapter = adapter

        contactViewModel.allContacts.observe(this) { words ->
            // Update the cached copy of the words in the adapter.
            words.let { adapter.updateList(it) }
        }
    }
}
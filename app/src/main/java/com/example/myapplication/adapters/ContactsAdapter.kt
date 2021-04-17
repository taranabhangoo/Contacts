package com.example.myapplication.adapters

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.models.DataModels.Contact
import com.example.myapplication.R
import com.example.myapplication.utils.inflate
import kotlin.collections.ArrayList

class ContactsAdapter() : RecyclerView.Adapter<ContactsAdapter.ContactHolder>() {

    private var contacts = ArrayList<Contact>()

    class ContactHolder(view: View) : RecyclerView.ViewHolder(view) {
        var titleView: TextView? = view.findViewById(R.id.name)
        var phoneView: TextView? = view.findViewById(R.id.phone_number)
        private var contact: Contact? = null

        @SuppressLint("SetTextI18n")
        fun bindContact(contact: Contact) {
            this.contact = contact
            titleView?.text = contact.name?.first
            phoneView?.text = contact.phones?.firstOrNull()?.value
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
        val inflatedView = parent.inflate(R.layout.contact_card, false)
        return ContactHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        val contact = contacts[position]
        holder.bindContact(contact)
    }

    override fun getItemCount() = contacts.size

    fun updateList(contactList: List<Contact>) {
        contacts = ArrayList(contactList)
        notifyDataSetChanged()
    }
}
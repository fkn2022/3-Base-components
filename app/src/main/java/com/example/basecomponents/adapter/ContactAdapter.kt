package com.example.basecomponents.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.basecomponents.R
import com.example.basecomponents.models.UserContact

class ContactAdapter : RecyclerView.Adapter<ContactViewHolder>() {

    private var contacts = listOf<UserContact>()

    fun setContacts(c: List<UserContact>) {
        contacts = c
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.contact_card, parent, false)
        )
    }

    override fun getItemCount(): Int = contacts.size

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.onBind(contacts[position])
    }
}
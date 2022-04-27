package com.example.basecomponents.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.basecomponents.R
import com.example.basecomponents.models.UserContact

class ContactViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val contactName = itemView.findViewById<TextView>(R.id.phoneNumber)
    private val contactNumber = itemView.findViewById<TextView>(R.id.contactName)

    fun onBind(userContact:UserContact){
        contactName.text = userContact.name
        contactNumber.text = userContact.phone
    }
}
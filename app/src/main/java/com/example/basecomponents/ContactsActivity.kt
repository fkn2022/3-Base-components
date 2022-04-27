package com.example.basecomponents

import android.app.AlarmManager
import android.content.ContentResolver
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.UserDictionary
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.basecomponents.adapter.ContactAdapter
import com.example.basecomponents.databinding.ActivityContactsBinding
import com.example.basecomponents.models.UserContact

class ContactsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onStart() {
        super.onStart()
        setupRecycler()
    }

    private fun setupRecycler(){
        binding.contactsRecycler.adapter = ContactAdapter()
        (binding.contactsRecycler.adapter as ContactAdapter).apply {
            setContacts(getContacts())
        }
        binding.contactsRecycler.layoutManager = LinearLayoutManager(this)
    }

    private fun getContacts(): List<UserContact> {
        val contacts = ArrayList<UserContact>()
        val uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val mProjection = arrayOf(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )
        val cursor = contentResolver.query(uri, mProjection, null, null, null)!!

        val indexName =
            cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
        val indexPhone = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
        cursor.use {
            if (it.moveToFirst()) {
                do {
                    val name = it.getString(indexName)
                    val phone = it.getString(indexPhone)
                    contacts.add(UserContact(phone, name))
                } while (it.moveToNext())
            }
        }

        return contacts
    }
}

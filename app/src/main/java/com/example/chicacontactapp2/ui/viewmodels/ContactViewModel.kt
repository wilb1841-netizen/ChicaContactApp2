package com.example.chicacontactapp2.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.chicacontactapp2.data.Contact
import com.example.chicacontactapp2.data.ContactDatabase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
// AndroidViewModel receives the Application in the constructor
// This allows the ViewModel to access the database which needs Application context
class ContactViewModel (application: Application): AndroidViewModel(application){
    // Get theDAO from the database singleton
    // The DAO was created and initialized in the ContactDatabase.getDatabase() method
    // so calling it again returns the same instance of the DAO
    private val _dao = ContactDatabase.getDatabase(context = application).contactDao()

    // Expose all contacts as a StateFlow to the UI layer
    // This is a read-only StateFlow that exposes the list of contacts
    //scope = viewModelScope: the flow lives as long as the viewModel does
    val contacts: StateFlow<List<Contact>> = _dao.getAllContacts().stateIn(
        scope =  viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = emptyList()
    )

    fun  saveContact(contact: Contact) {
        viewModelScope.launch {
            _dao.insertContact(contact)
        }
    }
    // Update a contact in the database
    // Challenge create the method for updating a contact
    // Challenge create the method for deleting a contact
    fun deleteContact(contact: Contact){
        viewModelScope.launch {
            _dao.deleteContact(contact)
        }
    }
}
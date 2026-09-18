package com.example.chicacontactapp2.ui.Screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.chicacontactapp2.data.Contact
import com.example.chicacontactapp2.ui.Components.ContactDialog
import com.example.chicacontactapp2.ui.Components.ContactItem
import com.example.chicacontactapp2.ui.viewmodels.ContactViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactListScreen(
    viewModel: ContactViewModel,
    onContactClick: (Contact)-> Unit
){
    // Evert time the viewmodel emits a new list of contacts,
    //this recomposes and shows the new list
    val contacts by viewModel.contacts.collectAsState()
    // Show the add dialog when the user clicks the + button
    var showAddDialog by remember { mutableStateOf(value = false) }
    //show the add dialog when the user clicks the + button
    if (showAddDialog) {
        ContactDialog(
            contact = null,
            onConfirm = { newContact ->
                //Viewmodel launches a coroutine to save the new contact
                viewModel.saveContact(newContact)
                //challenge Close the dialog after the contact
                showAddDialog = false
            },
            onDismiss = {
                showAddDialog = false
            }
        )
    }// end if
    Scaffold(
        topBar = {
            TopAppBar(
                // Title displayed in the center of the TopAppBar
                title = {Text("Contacts")},
                // Style the header with the app's primary color
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }, // end TopAppBar
        floatingActionButton = {
            // FAB --circular button in the bottom right corner
            FloatingActionButton( onClick = {showAddDialog = true }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Contact")
            } // end floatingActionButton parameter
        }
            ){ padding ->
            // check if there are any contacts
            if (contacts.isEmpty()){
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = "No contacts yet.\n Tap + to add a contact.",
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                }
            }else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    // Add vertical padding to keep content centered
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(contacts, key = { it.id }) { contact ->
                        ContactItem(
                            contact = contact,
                            // when the user taps the card, navigate to the detail screen
                            onClick = {onContactClick(contact)},
                            // When user taps the delete button, delete from ViewModel
                            //   launches a coroutine to delete from the dataBase
                            onDelete = {viewModel.deleteContact(contact)}
                        )
                    }
                }
            }
    }
}


















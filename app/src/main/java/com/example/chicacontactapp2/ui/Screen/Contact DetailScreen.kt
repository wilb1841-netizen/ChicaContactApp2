package com.example.chicacontactapp2.ui.Screen

import android.R.attr.text
import android.app.AlertDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.chicacontactapp2.ui.Components.ContactDialog
import com.example.chicacontactapp2.ui.viewmodels.ContactViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactDetailScreen(
    contactId: Int,
    viewModel: ContactViewModel,
    onBackClick: () -> Unit
) {
    val contacts by viewModel.contacts.collectAsState()
    val contact = contacts.firstOrNull { it.id == contactId }
    if (contact == null) {
        onBackClick()
        return
    }
    var showEditDialog by remember { mutableStateOf(value = false) }
    var showDeleteDialog by remember { mutableStateOf(value = false) }

    if (showEditDialog) {
        ContactDialog(
            contact = contact,
            onDismiss = { showEditDialog = false },
            onConfirm = { updateContact ->
                viewModel.updateContact(updateContact)
                showEditDialog = false
            }
        )
    }
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete contact") },
            text = { "Delete ${contact.name}? This action cannot be undone." },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteContact(contact)
                        showDeleteDialog = false
                        onBackClick()
                    }
                ) {
                    Text("Delete", color = MaterialTheme.colorScheme.error)
                }
            },
            // Challenge dismiss the delete dialog
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    } // end if showDeleteDialog
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Contact Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },


                actions = {
                    IconButton(onClick = { showEditDialog = true }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit Contact")
                    }
                    //Challenge add the Icon for the delete button
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Delete Contact",
                            tint = MaterialTheme.colorScheme.error)
                    }
                }
            )
        }

){
    padding ->
    Column(
        modifier = Modifier
            .padding(paddingValues = padding)
            .padding(24.dp)
            .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy (16.dp)
    ) {
        Text(
            contact.name,
            style = MaterialTheme.typography.headlineMedium
        )
        HorizontalDivider()
        Text(  "phoneNumber: ${contact.phoneNumber}",
        style = MaterialTheme.typography.bodyLarge
        )
        if (contact.email.isNotEmpty()){
        Text(
            text = "Email: ${contact.email}",
            style = MaterialTheme.typography.bodyMedium
        )
    }



    }

}


}




































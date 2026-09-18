package com.example.chicacontactapp2.ui.Components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.chicacontactapp2.data.Contact

// Reusable dialog for creating and editing contacts
// Contact: null = create mode (empty fields), Contact object = edit mode (pre-filled)
//onConfirm : callback when user taps Save-passes the new/ updated contact
//onDismiss: callback when user taps Cancel or closes the dialog
@Composable
fun ContactDialog(
    contact: Contact? = null,
    onConfirm: (Contact) -> Unit,
    onDismiss: () -> Unit
) {
    //Local state for the three put fields
    // these are separate from the viewModel -- purely UI state
    // They exit only whiled the dialog is visible and open
    var name by remember { mutableStateOf(value = contact?.name ?: "") }
    var phoneNumber by remember { mutableStateOf(value = contact?.phoneNumber ?: "") }
    var email by remember { mutableStateOf(value = contact?.email ?: "") }

    // Determine the title of the dialog based on the contact parameter
    // Contact: null = create mode (empty fields), Contact object = edit mode (pre-filled)
    val title = if (contact == null) "Add Contact" else "Edit Contact"

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = title) },
        text = {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                //Name field -- required
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(text = "Name") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )

                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    label = { Text(text = "phoneNumber") },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
                // Email fields-- optional
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text(text = "email(Optional)") },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)

                )


            }
        },
        confirmButton = {
            Button(onClick = {
                val newContact = Contact(
                    id = contact?.id ?: 0,
                    name = name,
                    phoneNumber = phoneNumber,
                    email = email
                )
                onConfirm(newContact)
            }) { Text("Save") }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }

    )
}
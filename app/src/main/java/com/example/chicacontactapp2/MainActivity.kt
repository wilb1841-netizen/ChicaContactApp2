package com.example.chicacontactapp2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chicacontactapp2.data.Contact
import com.example.chicacontactapp2.ui.Screen.ContactDetailScreen
import com.example.chicacontactapp2.ui.Screen.ContactListScreen
import com.example.chicacontactapp2.ui.viewmodels.ContactViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Create the viewModel instance
            // viewModel() is a compose function that creates a ViewModel instance
            // scoped to the current composable -- survives recompositions and configuration changes
            // All  screens receive the same ViewModel instance, so they share the same data
            val viewModel: ContactViewModel = viewModel()

            val selectedContact = remember { mutableStateOf<Contact?>(value = null) }
            // Apply the app's Material design theme to all screens
            MaterialTheme {
                // Conditional rendering -- show one screen or
                if (selectedContact.value == null) {
                    //show the list Screen -- user is browsing
                    ContactListScreen(
                        viewModel = viewModel,
                        onContactClick = { contact ->
                            selectedContact.value = contact
                        }
                    )
                }
                else{
                    ContactDetailScreen(
                        contactId = selectedContact.value!!.id,
                        viewModel = viewModel,
                        onBackClick = {selectedContact.value = null}
                    )
                }
            }
        }
    }
}


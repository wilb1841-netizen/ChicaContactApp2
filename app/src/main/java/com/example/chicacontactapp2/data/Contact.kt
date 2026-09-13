package com.example.chicacontactapp2.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
// data class automatically generates equals(), copy(), and toString()
// Room requires data class because it uses equals() to detect row changes
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    // The contact's phone number stored as text, not as a number
    // String preserves leading zeros, + signs, spaces, and international formats
    // Required — there is no default value so callers must always provide it

    val phoneNumber: String,
    // The contact's email address
    // Optional — defaults to empty string so callers do not have to provide it
    // An empty string means "no email" — we never store null for this field
    val email: String = ""

)
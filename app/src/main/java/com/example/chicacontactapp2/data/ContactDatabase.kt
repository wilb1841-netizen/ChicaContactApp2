package com.example.chicacontactapp2.data

import android.content.Context
import android.provider.CalendarContract
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.chicacontactapp2.ContactDao

@Database(entities = [Contact::class], version = 1, exportSchema = false)
abstract class ContactDatabase : RoomDatabase(){
    abstract  fun  contactDao() : ContactDao
    companion object{
        @Volatile
        private var INSTANCE: ContactDatabase? = null
        fun getDatabase(context: Context): ContactDatabase{
            return INSTANCE ?: synchronized(lock =this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    klass = ContactDatabase::class.java,
                    name = "contacts_database"
                ).build()
                //store the instance all future calls return this directly
                //This is a singleton
                //and skip the synchronized block
                INSTANCE = instance
                // return instance to the caller
                instance
            }
        }
    }
}
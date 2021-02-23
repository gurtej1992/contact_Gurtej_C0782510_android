package com.lambton.contact_gurtej_c0782510_android.DBHelper;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.lambton.contact_gurtej_c0782510_android.Model.Contact;

@Database(entities = { Contact.class }, version = 1)
 public abstract class ContactDatabase extends RoomDatabase {
 public abstract ContactDao getContactDeo();

 private static ContactDatabase contactDB;

 public static ContactDatabase getInstance(Context context) {
  if (null == contactDB) {
   contactDB = buildDatabaseInstance(context);
  }
  return contactDB;
 }

 private static ContactDatabase buildDatabaseInstance(Context context) {
  return Room.databaseBuilder(context,
          ContactDatabase.class,
          "phonebook")
          .allowMainThreadQueries().build();
 }

 public void cleanUp(){
  contactDB = null;
 }

}

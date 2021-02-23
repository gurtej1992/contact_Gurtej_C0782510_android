package com.lambton.contact_gurtej_c0782510_android;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface  ContactDao {
    @Query("SELECT * FROM Contact")
    List<Contact> getAll();


    /*
     * Insert the object in database
     * @param Contact, object to be inserted
     */
    @Insert
    void insert(Contact Contact);

    /*
     * update the object in database
     * @param Contact, object to be updated
     */
    @Update
    void update(Contact repos);

    /*
     * delete the object from database
     * @param Contact, object to be deleted
     */
    @Delete
    void delete(Contact Contact);

    /*
     * delete list of objects from database
     * @param Contact, array of objects to be deleted
     */
    @Delete
    void delete(Contact... Contact);      // Contact... is varargs, here Contact is an array

}

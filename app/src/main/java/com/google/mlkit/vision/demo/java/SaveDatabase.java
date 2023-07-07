/*
package com.google.mlkit.vision.demo.java;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ImageEntity.class}, version = 1)
public abstract class SaveDatabase extends RoomDatabase {

    */
/*public abstract ImageDao imageDao();*//*


    private static SaveDatabase INSTANCE;

    public static SaveDatabase getInstance(Context context) {

        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), SaveDatabase.class, "image_info_database").allowMainThreadQueries().build();
        }
        return INSTANCE;

    }

}
*/

package com.google.mlkit.vision.smartGallery.java.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Pics.class,Tags.class, PictTagCrossRef.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PictTagDao pictTagDao();
}
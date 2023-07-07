package com.google.mlkit.vision.smartGallery.java.Database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Pics{

    @NonNull @PrimaryKey public String  PicId ;
    public String PicName;
    //public File PicAsFile;
}

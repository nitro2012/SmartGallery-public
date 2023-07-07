package com.google.mlkit.vision.smartGallery.java.Database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class Tags{
    @PrimaryKey
    @NonNull public String tagName;

}




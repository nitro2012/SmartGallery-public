package com.google.mlkit.vision.smartGallery.java.Database;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(primaryKeys = {"tagName", "PicId"})
public class PictTagCrossRef {
    @NonNull public String tagName;
    @NonNull public String PicId;
}

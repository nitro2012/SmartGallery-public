package com.google.mlkit.vision.smartGallery.java.Database;


import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class TagsPicPair {
    @Embedded
    public Tags tags;
    @Relation(
            parentColumn = "tagName",
            entity = Pics.class,
            entityColumn = "PicId",
            associateBy = @Junction(value = PictTagCrossRef.class,
                    parentColumn = "tagName",
                    entityColumn = "PicId"
            )
    )
    public List<Pics> picsArray;
}
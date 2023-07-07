package com.google.mlkit.vision.smartGallery.java.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface PictTagDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertData( PictTagCrossRef join);//(PictTagCrossRef... picAndTag);
     @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertData( Tags tag);//(PictTagCrossRef... picAndTag);
     @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertData( Pics pic);//(PictTagCrossRef... picAndTag);
    @Transaction
    @Query("SELECT * FROM Tags where tagName=:tag")
    public List<TagsPicPair> getTagPicPair(String tag);



    /*@Query(" SELECT w AS picId " +
            "FROM picttagcrossref " +
            "WHERE tagName = :tag")
    public List<String> loadPics(String tag);*/
}

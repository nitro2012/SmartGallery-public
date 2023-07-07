package com.google.mlkit.vision.smartGallery.java.Database;

import android.database.Cursor;
import androidx.collection.ArrayMap;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class PictTagDao_Impl implements PictTagDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<PictTagCrossRef> __insertionAdapterOfPictTagCrossRef;

  private final EntityInsertionAdapter<Tags> __insertionAdapterOfTags;

  private final EntityInsertionAdapter<Pics> __insertionAdapterOfPics;

  public PictTagDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPictTagCrossRef = new EntityInsertionAdapter<PictTagCrossRef>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR IGNORE INTO `PictTagCrossRef` (`tagName`,`PicId`) VALUES (?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, PictTagCrossRef value) {
        if (value.tagName == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.tagName);
        }
        if (value.PicId == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.PicId);
        }
      }
    };
    this.__insertionAdapterOfTags = new EntityInsertionAdapter<Tags>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR IGNORE INTO `Tags` (`tagName`) VALUES (?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Tags value) {
        if (value.tagName == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.tagName);
        }
      }
    };
    this.__insertionAdapterOfPics = new EntityInsertionAdapter<Pics>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR IGNORE INTO `Pics` (`PicId`,`PicName`) VALUES (?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Pics value) {
        if (value.PicId == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.PicId);
        }
        if (value.PicName == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.PicName);
        }
      }
    };
  }

  @Override
  public void insertData(final PictTagCrossRef join) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfPictTagCrossRef.insert(join);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertData(final Tags tag) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfTags.insert(tag);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertData(final Pics pic) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfPics.insert(pic);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<TagsPicPair> getTagPicPair(final String tag) {
    final String _sql = "SELECT * FROM Tags where tagName=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (tag == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, tag);
    }
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
      try {
        final int _cursorIndexOfTagName = CursorUtil.getColumnIndexOrThrow(_cursor, "tagName");
        final ArrayMap<String, ArrayList<Pics>> _collectionPicsArray = new ArrayMap<String, ArrayList<Pics>>();
        while (_cursor.moveToNext()) {
          if (!_cursor.isNull(_cursorIndexOfTagName)) {
            final String _tmpKey = _cursor.getString(_cursorIndexOfTagName);
            ArrayList<Pics> _tmpPicsArrayCollection = _collectionPicsArray.get(_tmpKey);
            if (_tmpPicsArrayCollection == null) {
              _tmpPicsArrayCollection = new ArrayList<Pics>();
              _collectionPicsArray.put(_tmpKey, _tmpPicsArrayCollection);
            }
          }
        }
        _cursor.moveToPosition(-1);
        __fetchRelationshipPicsAscomGoogleMlkitVisionSmartGalleryJavaDatabasePics(_collectionPicsArray);
        final List<TagsPicPair> _result = new ArrayList<TagsPicPair>(_cursor.getCount());
        while(_cursor.moveToNext()) {
          final TagsPicPair _item;
          final Tags _tmpTags;
          if (! (_cursor.isNull(_cursorIndexOfTagName))) {
            _tmpTags = new Tags();
            if (_cursor.isNull(_cursorIndexOfTagName)) {
              _tmpTags.tagName = null;
            } else {
              _tmpTags.tagName = _cursor.getString(_cursorIndexOfTagName);
            }
          }  else  {
            _tmpTags = null;
          }
          ArrayList<Pics> _tmpPicsArrayCollection_1 = null;
          if (!_cursor.isNull(_cursorIndexOfTagName)) {
            final String _tmpKey_1 = _cursor.getString(_cursorIndexOfTagName);
            _tmpPicsArrayCollection_1 = _collectionPicsArray.get(_tmpKey_1);
          }
          if (_tmpPicsArrayCollection_1 == null) {
            _tmpPicsArrayCollection_1 = new ArrayList<Pics>();
          }
          _item = new TagsPicPair();
          _item.tags = _tmpTags;
          _item.picsArray = _tmpPicsArrayCollection_1;
          _result.add(_item);
        }
        __db.setTransactionSuccessful();
        return _result;
      } finally {
        _cursor.close();
        _statement.release();
      }
    } finally {
      __db.endTransaction();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }

  private void __fetchRelationshipPicsAscomGoogleMlkitVisionSmartGalleryJavaDatabasePics(
      final ArrayMap<String, ArrayList<Pics>> _map) {
    final Set<String> __mapKeySet = _map.keySet();
    if (__mapKeySet.isEmpty()) {
      return;
    }
    // check if the size is too big, if so divide;
    if(_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      ArrayMap<String, ArrayList<Pics>> _tmpInnerMap = new ArrayMap<String, ArrayList<Pics>>(androidx.room.RoomDatabase.MAX_BIND_PARAMETER_CNT);
      int _tmpIndex = 0;
      int _mapIndex = 0;
      final int _limit = _map.size();
      while(_mapIndex < _limit) {
        _tmpInnerMap.put(_map.keyAt(_mapIndex), _map.valueAt(_mapIndex));
        _mapIndex++;
        _tmpIndex++;
        if(_tmpIndex == RoomDatabase.MAX_BIND_PARAMETER_CNT) {
          __fetchRelationshipPicsAscomGoogleMlkitVisionSmartGalleryJavaDatabasePics(_tmpInnerMap);
          _tmpInnerMap = new ArrayMap<String, ArrayList<Pics>>(RoomDatabase.MAX_BIND_PARAMETER_CNT);
          _tmpIndex = 0;
        }
      }
      if(_tmpIndex > 0) {
        __fetchRelationshipPicsAscomGoogleMlkitVisionSmartGalleryJavaDatabasePics(_tmpInnerMap);
      }
      return;
    }
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `Pics`.`PicId` AS `PicId`,`Pics`.`PicName` AS `PicName`,_junction.`tagName` FROM `PictTagCrossRef` AS _junction INNER JOIN `Pics` ON (_junction.`PicId` = `Pics`.`PicId`) WHERE _junction.`tagName` IN (");
    final int _inputSize = __mapKeySet.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (String _item : __mapKeySet) {
      if (_item == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, _item);
      }
      _argIndex ++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, false, null);
    try {
      final int _itemKeyIndex = 2; // _junction.tagName;
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfPicId = CursorUtil.getColumnIndexOrThrow(_cursor, "PicId");
      final int _cursorIndexOfPicName = CursorUtil.getColumnIndexOrThrow(_cursor, "PicName");
      while(_cursor.moveToNext()) {
        if (!_cursor.isNull(_itemKeyIndex)) {
          final String _tmpKey = _cursor.getString(_itemKeyIndex);
          ArrayList<Pics> _tmpRelation = _map.get(_tmpKey);
          if (_tmpRelation != null) {
            final Pics _item_1;
            _item_1 = new Pics();
            if (_cursor.isNull(_cursorIndexOfPicId)) {
              _item_1.PicId = null;
            } else {
              _item_1.PicId = _cursor.getString(_cursorIndexOfPicId);
            }
            if (_cursor.isNull(_cursorIndexOfPicName)) {
              _item_1.PicName = null;
            } else {
              _item_1.PicName = _cursor.getString(_cursorIndexOfPicName);
            }
            _tmpRelation.add(_item_1);
          }
        }
      }
    } finally {
      _cursor.close();
    }
  }
}

package com.google.mlkit.vision.smartGallery.java.Database;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile PictTagDao _pictTagDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Pics` (`PicId` TEXT NOT NULL, `PicName` TEXT, PRIMARY KEY(`PicId`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Tags` (`tagName` TEXT NOT NULL, PRIMARY KEY(`tagName`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `PictTagCrossRef` (`tagName` TEXT NOT NULL, `PicId` TEXT NOT NULL, PRIMARY KEY(`tagName`, `PicId`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '3df237b743483a21218d1060e2520a93')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `Pics`");
        _db.execSQL("DROP TABLE IF EXISTS `Tags`");
        _db.execSQL("DROP TABLE IF EXISTS `PictTagCrossRef`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsPics = new HashMap<String, TableInfo.Column>(2);
        _columnsPics.put("PicId", new TableInfo.Column("PicId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPics.put("PicName", new TableInfo.Column("PicName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPics = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPics = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPics = new TableInfo("Pics", _columnsPics, _foreignKeysPics, _indicesPics);
        final TableInfo _existingPics = TableInfo.read(_db, "Pics");
        if (! _infoPics.equals(_existingPics)) {
          return new RoomOpenHelper.ValidationResult(false, "Pics(com.google.mlkit.vision.smartGallery.java.Database.Pics).\n"
                  + " Expected:\n" + _infoPics + "\n"
                  + " Found:\n" + _existingPics);
        }
        final HashMap<String, TableInfo.Column> _columnsTags = new HashMap<String, TableInfo.Column>(1);
        _columnsTags.put("tagName", new TableInfo.Column("tagName", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTags = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTags = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTags = new TableInfo("Tags", _columnsTags, _foreignKeysTags, _indicesTags);
        final TableInfo _existingTags = TableInfo.read(_db, "Tags");
        if (! _infoTags.equals(_existingTags)) {
          return new RoomOpenHelper.ValidationResult(false, "Tags(com.google.mlkit.vision.smartGallery.java.Database.Tags).\n"
                  + " Expected:\n" + _infoTags + "\n"
                  + " Found:\n" + _existingTags);
        }
        final HashMap<String, TableInfo.Column> _columnsPictTagCrossRef = new HashMap<String, TableInfo.Column>(2);
        _columnsPictTagCrossRef.put("tagName", new TableInfo.Column("tagName", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPictTagCrossRef.put("PicId", new TableInfo.Column("PicId", "TEXT", true, 2, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPictTagCrossRef = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPictTagCrossRef = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPictTagCrossRef = new TableInfo("PictTagCrossRef", _columnsPictTagCrossRef, _foreignKeysPictTagCrossRef, _indicesPictTagCrossRef);
        final TableInfo _existingPictTagCrossRef = TableInfo.read(_db, "PictTagCrossRef");
        if (! _infoPictTagCrossRef.equals(_existingPictTagCrossRef)) {
          return new RoomOpenHelper.ValidationResult(false, "PictTagCrossRef(com.google.mlkit.vision.smartGallery.java.Database.PictTagCrossRef).\n"
                  + " Expected:\n" + _infoPictTagCrossRef + "\n"
                  + " Found:\n" + _existingPictTagCrossRef);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "3df237b743483a21218d1060e2520a93", "709e276deadd6362bc155f6fcbc0205b");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "Pics","Tags","PictTagCrossRef");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `Pics`");
      _db.execSQL("DELETE FROM `Tags`");
      _db.execSQL("DELETE FROM `PictTagCrossRef`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(PictTagDao.class, PictTagDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  public PictTagDao pictTagDao() {
    if (_pictTagDao != null) {
      return _pictTagDao;
    } else {
      synchronized(this) {
        if(_pictTagDao == null) {
          _pictTagDao = new PictTagDao_Impl(this);
        }
        return _pictTagDao;
      }
    }
  }
}

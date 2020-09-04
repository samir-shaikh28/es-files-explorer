/*
 * Copyright (C) 2014-2020 Arpit Khurana <arpitkh96@gmail.com>, Vishal Nehra <vishalmeham2@gmail.com>,
 * Emmanuel Messulam<emmanuelbendavid@gmail.com>, Raymond Lai <airwave209gt at gmail.com> and Contributors.
 *
 * This file is part of Amaze File Manager.
 *
 * Amaze File Manager is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.droidtechlab.filemanager.database;

import com.droidtechlab.filemanager.database.daos.CloudEntryDao;
import com.droidtechlab.filemanager.database.daos.EncryptedEntryDao;
import com.droidtechlab.filemanager.database.daos.SortDao;
import com.droidtechlab.filemanager.database.daos.TabDao;
import com.droidtechlab.filemanager.database.models.explorer.CloudEntry;
import com.droidtechlab.filemanager.database.models.explorer.EncryptedEntry;
import com.droidtechlab.filemanager.database.models.explorer.Sort;
import com.droidtechlab.filemanager.database.models.explorer.Tab;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * Repository for {@link Tab}, {@link Sort}, {@link EncryptedEntry}, {@link CloudEntry} in
 * explorer.db in Amaze.
 *
 * @see RoomDatabase
 */
@Database(
    entities = {Tab.class, Sort.class, EncryptedEntry.class, CloudEntry.class},
    version = 7,
    exportSchema = false)
public abstract class ExplorerDatabase extends RoomDatabase {

  private static final String DATABASE_NAME = "explorer.db";
  private static final int DATABASE_VERSION = 7;

  public static final String TABLE_TAB = "tab";
  public static final String TABLE_CLOUD_PERSIST = "cloud";
  public static final String TABLE_ENCRYPTED = "encrypted";
  public static final String TABLE_SORT = "sort";

  public static final String COLUMN_TAB_NO = "tab_no";
  public static final String COLUMN_PATH = "path";
  public static final String COLUMN_HOME = "home";

  public static final String COLUMN_ENCRYPTED_ID = "_id";
  public static final String COLUMN_ENCRYPTED_PATH = "path";
  public static final String COLUMN_ENCRYPTED_PASSWORD = "password";

  public static final String COLUMN_CLOUD_ID = "_id";
  public static final String COLUMN_CLOUD_SERVICE = "service";
  public static final String COLUMN_CLOUD_PERSIST = "persist";

  public static final String COLUMN_SORT_PATH = "path";
  public static final String COLUMN_SORT_TYPE = "type";

  private static final String TEMP_TABLE_PREFIX = "temp_";

  private static final Migration MIGRATION_6_7 =
      new Migration(6, DATABASE_VERSION) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
          database.execSQL(
              "CREATE TABLE "
                  + TEMP_TABLE_PREFIX
                  + TABLE_TAB
                  + "("
                  + COLUMN_TAB_NO
                  + " INTEGER PRIMARY KEY NOT NULL, "
                  + COLUMN_PATH
                  + " TEXT, "
                  + COLUMN_HOME
                  + " TEXT)");
          database.execSQL(
              "INSERT INTO " + TEMP_TABLE_PREFIX + TABLE_TAB + " SELECT * FROM " + TABLE_TAB);
          database.execSQL("DROP TABLE " + TABLE_TAB);
          database.execSQL(
              "ALTER TABLE " + TEMP_TABLE_PREFIX + TABLE_TAB + " RENAME TO " + TABLE_TAB);

          database.execSQL(
              "CREATE TABLE "
                  + TEMP_TABLE_PREFIX
                  + TABLE_SORT
                  + "("
                  + COLUMN_SORT_PATH
                  + " TEXT PRIMARY KEY NOT NULL, "
                  + COLUMN_SORT_TYPE
                  + " INTEGER NOT NULL)");
          database.execSQL(
              "INSERT INTO " + TEMP_TABLE_PREFIX + TABLE_SORT + " SELECT * FROM " + TABLE_SORT);
          database.execSQL("DROP TABLE " + TABLE_SORT);
          database.execSQL(
              "ALTER TABLE " + TEMP_TABLE_PREFIX + TABLE_SORT + " RENAME TO " + TABLE_SORT);

          database.execSQL(
              "CREATE TABLE "
                  + TEMP_TABLE_PREFIX
                  + TABLE_ENCRYPTED
                  + "("
                  + COLUMN_ENCRYPTED_ID
                  + " INTEGER PRIMARY KEY NOT NULL,"
                  + COLUMN_ENCRYPTED_PATH
                  + " TEXT,"
                  + COLUMN_ENCRYPTED_PASSWORD
                  + " TEXT)");
          database.execSQL(
              "INSERT INTO "
                  + TEMP_TABLE_PREFIX
                  + TABLE_ENCRYPTED
                  + " SELECT * FROM "
                  + TABLE_ENCRYPTED);
          database.execSQL("DROP TABLE " + TABLE_ENCRYPTED);
          database.execSQL(
              "ALTER TABLE "
                  + TEMP_TABLE_PREFIX
                  + TABLE_ENCRYPTED
                  + " RENAME TO "
                  + TABLE_ENCRYPTED);

          database.execSQL(
              "CREATE TABLE "
                  + TEMP_TABLE_PREFIX
                  + TABLE_CLOUD_PERSIST
                  + "("
                  + COLUMN_CLOUD_ID
                  + " INTEGER PRIMARY KEY NOT NULL,"
                  + COLUMN_CLOUD_SERVICE
                  + " INTEGER,"
                  + COLUMN_CLOUD_PERSIST
                  + " TEXT)");
          database.execSQL(
              "INSERT INTO "
                  + TEMP_TABLE_PREFIX
                  + TABLE_CLOUD_PERSIST
                  + " SELECT * FROM "
                  + TABLE_CLOUD_PERSIST);
          database.execSQL("DROP TABLE " + TABLE_CLOUD_PERSIST);
          database.execSQL(
              "ALTER TABLE "
                  + TEMP_TABLE_PREFIX
                  + TABLE_CLOUD_PERSIST
                  + " RENAME TO "
                  + TABLE_CLOUD_PERSIST);
        }
      };

  protected abstract TabDao tabDao();

  protected abstract SortDao sortDao();

  protected abstract EncryptedEntryDao encryptedEntryDao();

  protected abstract CloudEntryDao cloudEntryDao();

  public static synchronized ExplorerDatabase initialize(@NonNull Context context) {
    return Room.databaseBuilder(context, ExplorerDatabase.class, DATABASE_NAME)
        .addMigrations(MIGRATION_6_7)
        .allowMainThreadQueries()
        .build();
  }
}

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

package com.droidtechlab.filemanager.database.daos;

import static com.droidtechlab.filemanager.database.UtilitiesDatabase.COLUMN_NAME;
import static com.droidtechlab.filemanager.database.UtilitiesDatabase.COLUMN_PATH;
import static com.droidtechlab.filemanager.database.UtilitiesDatabase.TABLE_BOOKMARKS;

import com.droidtechlab.filemanager.database.models.utilities.Bookmark;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * {@link Dao} interface definition for {@link Bookmark}. Concrete class is generated by Room during
 * build.
 *
 * @see Dao
 * @see Bookmark
 * @see com.droidtechlab.filemanager.database.UtilitiesDatabase
 */
@Dao
public interface BookmarkEntryDao {

  @Insert
  Completable insert(Bookmark instance);

  @Update
  Completable update(Bookmark instance);

  @Query("SELECT * FROM " + TABLE_BOOKMARKS)
  Single<List<Bookmark>> list();

  @Query(
          "SELECT * FROM "
                  + TABLE_BOOKMARKS
                  + " WHERE "
                  + COLUMN_NAME
                  + " = :name AND "
                  + COLUMN_PATH
                  + " = :path")
  Single<Bookmark> findByNameAndPath(String name, String path);

  @Query(
          "DELETE FROM "
                  + TABLE_BOOKMARKS
                  + " WHERE "
                  + COLUMN_NAME
                  + " = :name AND "
                  + COLUMN_PATH
                  + " = :path")
  Completable deleteByNameAndPath(String name, String path);
}
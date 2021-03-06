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

import static com.droidtechlab.filemanager.database.ExplorerDatabase.COLUMN_PATH;
import static com.droidtechlab.filemanager.database.ExplorerDatabase.TABLE_ENCRYPTED;

import com.droidtechlab.filemanager.database.models.explorer.EncryptedEntry;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

/**
 * {@link Dao} interface definition for {@link EncryptedEntry}. Concrete class is generated by Room
 * during build.
 *
 * @see Dao
 * @see EncryptedEntry
 * @see com.droidtechlab.filemanager.database.ExplorerDatabase
 */
@Dao
public interface EncryptedEntryDao {

  @Insert
  public void insert(EncryptedEntry entry);

  @Query("SELECT * FROM " + TABLE_ENCRYPTED + " WHERE " + COLUMN_PATH + " = :path")
  public EncryptedEntry select(String path);

  @Update
  public void update(EncryptedEntry entry);

  @Transaction
  @Query("DELETE FROM " + TABLE_ENCRYPTED + " WHERE " + COLUMN_PATH + " = :path")
  public void delete(String path);

  @Query("SELECT * FROM " + TABLE_ENCRYPTED)
  public EncryptedEntry[] list();
}

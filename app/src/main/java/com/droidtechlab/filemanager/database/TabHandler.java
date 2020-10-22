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

import android.util.Log;

import com.droidtechlab.filemanager.application.AppConfig;
import com.droidtechlab.filemanager.database.models.explorer.Tab;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import io.reactivex.schedulers.Schedulers;

/** Created by Vishal on 9/17/2014. */
public class TabHandler {

  private final ExplorerDatabase database;

  private TabHandler(@NonNull ExplorerDatabase explorerDatabase) {
    this.database = explorerDatabase;
  }

  private static class TabHandlerHolder {
    private static final TabHandler INSTANCE =
            new TabHandler(AppConfig.getInstance().getExplorerDatabase());
  }

  public static TabHandler getInstance() {
    return TabHandlerHolder.INSTANCE;
  }

  public void addTab(@NonNull Tab tab) {
    database.tabDao().insertTab(tab).subscribeOn(Schedulers.io()).subscribe();
  }

  public void update(Tab tab) {
    database.tabDao().update(tab);
  }

  public void clear() {
    database.tabDao().clear().subscribeOn(Schedulers.io()).subscribe();
  }

  @Nullable
  public Tab findTab(int tabNo) {
    try {
      return database.tabDao().find(tabNo).subscribeOn(Schedulers.io()).blockingGet();
    } catch (Exception e) {
      // catch error to handle Single#onError for blockingGet
      Log.e(getClass().getSimpleName(), e.getMessage());
      return null;
    }
  }

  public Tab[] getAllTabs() {
    List<Tab> tabList = database.tabDao().list().subscribeOn(Schedulers.io()).blockingGet();
    Tab[] tabs = new Tab[tabList.size()];
    return tabList.toArray(tabs);
  }
}
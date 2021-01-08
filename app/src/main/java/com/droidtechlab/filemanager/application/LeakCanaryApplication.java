///*
// * Copyright (C) 2014-2020 Arpit Khurana <arpitkh96@gmail.com>, Vishal Nehra <vishalmeham2@gmail.com>,
// * Emmanuel Messulam<emmanuelbendavid@gmail.com>, Raymond Lai <airwave209gt at gmail.com> and Contributors.
// *
// * This file is part of Amaze File Manager.
// *
// * Amaze File Manager is free software: you can redistribute it and/or modify
// * it under the terms of the GNU General Public License as published by
// * the Free Software Foundation, either version 3 of the License, or
// * (at your option) any later version.
// *
// * This program is distributed in the hope that it will be useful,
// * but WITHOUT ANY WARRANTY; without even the implied warranty of
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// * GNU General Public License for more details.
// *
// * You should have received a copy of the GNU General Public License
// * along with this program.  If not, see <http://www.gnu.org/licenses/>.
// */
//
//package com.droidtechlab.filemanager.application;
//
//import androidx.multidex.MultiDexApplication;
//
//import com.squareup.leakcanary.LeakCanary;
//
///** @author Emmanuel on 28/8/2017, at 18:12. */
//public class LeakCanaryApplication extends MultiDexApplication {
//
//  @Override
//  public void onCreate() {
//    super.onCreate();
//    if (LeakCanary.isInAnalyzerProcess(this)) {
//        // This process is dedicated to LeakCanary for heap analysis.
//        // You should not init your app in this process.
//        return;
//    }
//    LeakCanary.install(this);
//  }
//}

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

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.droidtechlab.filemanager.database.models.OperationData;
import com.droidtechlab.filemanager.filesystem.ssh.SshClientUtils;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

@RunWith(AndroidJUnit4.class)
public class UtilsHandlerTest {

  private UtilitiesDatabase utilitiesDatabase;

  private UtilsHandler utilsHandler;

  @Before
  public void setUp() {
    Context ctx = InstrumentationRegistry.getInstrumentation().getTargetContext();
    utilitiesDatabase = UtilitiesDatabase.initialize(ctx);
    utilsHandler = new UtilsHandler(ctx, utilitiesDatabase);
    utilitiesDatabase.getOpenHelper().getWritableDatabase().execSQL("DELETE FROM sftp;");
  }

  @After
  public void tearDown() {
    if (utilitiesDatabase.isOpen()) utilitiesDatabase.close();
  }

  @Test
  public void testEncodeEncryptUri1() {
    performTest("ssh://test:testP@ssw0rd@127.0.0.1:5460");
  }

  @Test
  public void testEncodeEncryptUri2() {
    performTest("ssh://test:testP@##word@127.0.0.1:22");
  }

  @Test
  public void testEncodeEncryptUri3() {
    performTest("ssh://test@example.com:testP@ssw0rd@127.0.0.1:22");
  }

  @Test
  public void testEncodeEncryptUri4() {
    performTest("ssh://test@example.com:testP@ssw0##$@127.0.0.1:22");
  }

  private void performTest(@NonNull final String origPath) {
    String encryptedPath = SshClientUtils.encryptSshPathAsNecessary(origPath);

    utilsHandler.saveToDatabase(
        new OperationData(
            UtilsHandler.Operation.SFTP,
            encryptedPath,
            "Test",
            "00:11:22:33:44:55:66:77:88:99:aa:bb:cc:dd:ee:ff",
            null,
            null));

    await()
        .atMost(10, TimeUnit.SECONDS)
        .until(
            () -> {
              List<String[]> result = utilsHandler.getSftpList();
              assertEquals(1, result.size());
              assertEquals("Test", result.get(0)[0]);
              assertEquals(origPath, result.get(0)[1]);
              assertEquals(
                  "00:11:22:33:44:55:66:77:88:99:aa:bb:cc:dd:ee:ff",
                  utilsHandler.getSshHostKey(origPath));
              return true;
            });
  }
}

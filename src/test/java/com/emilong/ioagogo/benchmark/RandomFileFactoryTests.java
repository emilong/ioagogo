package com.emilong.ioagogo.benchmark;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import java.util.Random;

public class RandomFileFactoryTests {
  @Rule
  public TemporaryFolder temporaryFolder = new TemporaryFolder();

  private final Random random = new Random();

  private int fileSize;
  private File folder;
  private File file;

  /**
    * Setup the file factory in the temporary folder.
    */
  @Before
  public void setupFileFactory() throws IOException {
    fileSize = 2 + random.nextInt(1 << 16);
    folder = temporaryFolder.newFolder();
    file = new RandomFileFactory(fileSize, folder).getFile();
  }

  @After
  public void removeFile() {
    file.delete();
  }

  @Test
  public void shouldCreateAFileOfTheRightLength() throws IOException {
    assertThat(file.length(), equalTo((long) fileSize));
  }

  @Test
  public void shouldCreateAFileInTheGivenFolder() throws IOException {
    assertThat(file.getParentFile(), equalTo(folder));
  }
}

package com.emilong.ioagogo.benchmark;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.List;
import java.util.Random;

import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import org.junit.rules.TemporaryFolder;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.intThat;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RandomFileFactoryTests {
  @Rule
  public TemporaryFolder temporaryFolder = new TemporaryFolder();

  private final Random random = new Random();

  private int fileSize;
  private File folder;
  private File file;

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

package com.emilong.ioagogo.strategies;

import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileOutputStream;

import java.util.Random;

public class ReadStrategyTemporaryFile extends TemporaryFolder {
  public static final int DEFAULT_SIZE = 1 << 16;

  private final Random random = new Random();
  private final int size;

  private int value;
  private File inputFile;

  public ReadStrategyTemporaryFile(int size) {
    this.size = size;
  }

  public ReadStrategyTemporaryFile() {
    this(DEFAULT_SIZE);
  }

  @Override
  public void before() throws Throwable {
    super.before();

    inputFile = newFile();
    value = random.nextInt(256);

    FileOutputStream outputStream = null;

    try {
      outputStream = new FileOutputStream(inputFile);

      for (int i = 0; i < size - 1; i++) {
        outputStream.write(0);
      }

      outputStream.write(value);
    } finally {
      if (outputStream != null) {
        outputStream.close();
      }
    }
  }

  public int getValue() {
    return value;
  }

  public File getInputFile() {
    return inputFile;
  }
}

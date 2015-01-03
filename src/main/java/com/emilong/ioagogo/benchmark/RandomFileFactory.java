package com.emilong.ioagogo.benchmark;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.Random;

/**
 * An IFileFactory that generates a file of the given size,
 * filled with random bytes, in th specified directory.
 */
public class RandomFileFactory implements IFileFactory {
  private static final String PREFIX = "ioagogo";
  private static final String SUFFIX = ".bin";
  private static final int BUFFER_SIZE = 1 << 16;

  private final File file;
  private final Random random = new Random();

  /**
    * Creates a new RandomFileFactory with the given fileSize and parent
    * directory. May throw IOException if unable to create the file.
    */
  public RandomFileFactory(int fileSize, File directory) throws IOException {
    file = File.createTempFile(PREFIX, SUFFIX, directory);
    byte[] buffer = new byte[BUFFER_SIZE];

    FileOutputStream outputStream = null;

    try {
      outputStream = new FileOutputStream(file);

      for (int written = 0; written < fileSize;) {
        int thisChunk = Math.min(BUFFER_SIZE, fileSize - written);
        random.nextBytes(buffer);
        outputStream.write(buffer, 0, thisChunk);

        written += thisChunk;
      }
    } finally {
      if (outputStream != null) {
        outputStream.close();
      }
    }
  }

  @Override
  public File getFile() throws IOException {
    return file;
  }
}

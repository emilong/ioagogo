package com.emilong.ioagogo.strategies;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
  * An IReadStrategy that reads the bytes from the given file
  * using the byte array read method of RandomAccessFile.
***/
public class RandomAccessFileWithByteArrayReadsStrategy implements IReadStrategy {
  @Override
  public boolean supportsBufferSize(int bufferSize) {
    return true;
  }

  @Override
  public byte[] readBytes(int bufferSize, File inputFile) throws IOException {
    byte[] buffer = new byte[bufferSize];

    RandomAccessFile randomAccessFile = null;

    try {
      randomAccessFile = new RandomAccessFile(inputFile, "r");

      while (randomAccessFile.read(buffer, 0, bufferSize) >= 0) {}
    } finally {
      if (randomAccessFile != null) {
        randomAccessFile.close();
      }
    }

    return buffer;
  }

  @Override
  public String getDescription() {
    return "RandomAccessFile with byte array reads";
  }
}

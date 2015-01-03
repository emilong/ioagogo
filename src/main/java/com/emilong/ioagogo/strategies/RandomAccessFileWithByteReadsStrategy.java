package com.emilong.ioagogo.strategies;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
  * An IReadStrategy that reads the bytes from the given file one
  * at a time through a RandomAccessFile.
***/
public class RandomAccessFileWithByteReadsStrategy implements IReadStrategy {
  @Override
  public boolean supportsBufferSize(int bufferSize) {
    return bufferSize == 1;
  }

  @Override
  public byte[] readBytes(int bufferSize, File inputFile) throws IOException {
    byte[] buffer = new byte[1];

    RandomAccessFile randomAccessFile = null;

    try {
      randomAccessFile = new RandomAccessFile(inputFile, "r");

      for (int i = 0; i < inputFile.length(); i++) {
        buffer[0] = (byte) randomAccessFile.read();
      }
    } finally {
      if (randomAccessFile != null) {
        randomAccessFile.close();
      }
    }

    return buffer;
  }

  @Override
  public String getDescription() {
    return "RandomAccessFile with byte reads";
  }
}

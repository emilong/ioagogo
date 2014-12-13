package com.emilong.ioagogo.strategies;

import java.io.File;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
  * An IReadStrategy that reads the bytes from the given file using
  * the byte array read method of BufferedInputStream.
***/
public class BufferedInputStreamWithByteArrayReadsStrategy implements IReadStrategy {
  @Override
  public boolean supportsBufferSize(int bufferSize) {
    return true;
  }

  @Override
  public byte[] readBytes(int bufferSize, File inputFile) throws IOException {
    byte[] buffer = new byte[bufferSize];

    BufferedInputStream inputStream = null;

    try {
      inputStream = new BufferedInputStream(new FileInputStream(inputFile));

      while (inputStream.read(buffer, 0, bufferSize) >= 0) {}
    }
    finally {
      if (inputStream != null) {
        inputStream.close();
      }
    }

    return buffer;
  }
}

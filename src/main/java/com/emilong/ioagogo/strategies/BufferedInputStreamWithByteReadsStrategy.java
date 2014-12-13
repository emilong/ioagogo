package com.emilong.ioagogo.strategies;

import java.io.File;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
  * An IReadStrategy that reads the bytes from the given file one
  * at a time through a BufferedInputStream.
***/
public class BufferedInputStreamWithByteReadsStrategy implements IReadStrategy {
  @Override
  public boolean supportsBufferSize(int bufferSize) {
    return bufferSize == 1;
  }

  @Override
  public byte[] readBytes(int bufferSize, File inputFile) throws IOException {
    byte[] buffer = new byte[1];

    BufferedInputStream inputStream = null;

    try {
      inputStream = new BufferedInputStream(new FileInputStream(inputFile));

      for (int i = 0; i < inputFile.length(); i++) {
        buffer[0] = (byte) inputStream.read();
      }
    }
    finally {
      if (inputStream != null) {
        inputStream.close();
      }
    }

    return buffer;
  }
}

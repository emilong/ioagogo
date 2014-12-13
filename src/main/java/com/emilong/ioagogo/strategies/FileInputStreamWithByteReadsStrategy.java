package com.emilong.ioagogo.strategies;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
  * An IReadStrategy that reads the bytes from the given file one
  * at a time through a FileInputStream.
***/
public class FileInputStreamWithByteReadsStrategy implements IReadStrategy {
  @Override
  public boolean supportsBufferSize(int bufferSize) {
    return bufferSize == 1;
  }

  @Override
  public byte[] readBytes(int bufferSize, File inputFile) throws IOException {
    byte[] buffer = new byte[1];

    FileInputStream inputStream = null;

    try {
      inputStream = new FileInputStream(inputFile);

      for (int i = 0; i < inputFile.length(); i++) {
        buffer[0] = (byte) inputStream.read();
      }
    } finally {
      if (inputStream != null) {
        inputStream.close();
      }
    }

    return buffer;
  }
}

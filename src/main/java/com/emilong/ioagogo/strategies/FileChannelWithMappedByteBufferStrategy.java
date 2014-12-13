package com.emilong.ioagogo.strategies;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
  * An IReadStrategy that reads the bytes from the given file
  * through a FileChannel with a MappedByteBuffer.
***/
public class FileChannelWithMappedByteBufferStrategy implements IReadStrategy {
  @Override
  public boolean supportsBufferSize(int bufferSize) {
    return true;
  }

  @Override
  public byte[] readBytes(int bufferSize, File inputFile) throws IOException {
    byte[] buffer = new byte[bufferSize];
    FileInputStream inputStream = null;

    try {
      inputStream = new FileInputStream(inputFile);
      FileChannel fileChannel = inputStream.getChannel();
      ByteBuffer byteBuffer =
        fileChannel.map(FileChannel.MapMode.READ_ONLY, 0L, fileChannel.size());

      while (byteBuffer.hasRemaining()) {
        int readSize = Math.min(byteBuffer.remaining(), bufferSize);
        byteBuffer.get(buffer, 0, readSize);
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

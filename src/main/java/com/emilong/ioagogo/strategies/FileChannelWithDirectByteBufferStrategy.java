package com.emilong.ioagogo.strategies;

import java.io.IOException;

import java.nio.ByteBuffer;

/**
  * An IReadStrategy that reads the bytes from the given file
  * through a FileChannel with a direct allocated ByteBuffer.
***/
public class FileChannelWithDirectByteBufferStrategy
    extends FileChannelWithByteBufferStrategy {

  @Override
  protected ByteBufferFactory createByteBufferFactory(int bufferSize)
      throws IOException {
    return new DirectByteBufferFactory(bufferSize);
  }

  private class DirectByteBufferFactory extends ByteBufferFactory {
    public DirectByteBufferFactory(int bufferSize) throws IOException {
      super(bufferSize);

      this.byteBuffer = ByteBuffer.allocateDirect(bufferSize);
    }

    @Override
    public byte[] getBytes() throws IOException {
      byte[] buffer = new byte[bufferSize];
      byteBuffer.get(buffer, 0, bufferSize);
      return buffer;
    }
  }
}

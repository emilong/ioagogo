package com.emilong.ioagogo.strategies;

import java.io.IOException;

import java.nio.ByteBuffer;

/**
  * An IReadStrategy that reads the bytes from the given file
  * through a FileChannel with a ByteBuffer that wraps an allocated
  * byte array.
***/
public class FileChannelWithWrappedByteBufferStrategy
  extends FileChannelWithByteBufferStrategy {

  @Override
  protected ByteBufferFactory createByteBufferFactory(int bufferSize)
    throws IOException
  {
    return new WrappedByteBufferFactory(bufferSize);
  }

  private class WrappedByteBufferFactory extends ByteBufferFactory {
    private final byte[] buffer;

    public WrappedByteBufferFactory(int bufferSize) throws IOException {
      super(bufferSize);

      this.buffer = new byte[bufferSize];
      this.byteBuffer = ByteBuffer.wrap(buffer);
    }

    @Override
    public byte[] getBytes() throws IOException {
      return buffer;
    }
  }
}

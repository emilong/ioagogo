package com.emilong.ioagogo.strategies;

import java.io.IOException;

import java.nio.ByteBuffer;

/**
  * An IReadStrategy that reads the bytes from the given file
  * through a FileChannel with a normal allocated ByteBuffer.
***/
public class FileChannelWithAllocatedByteBufferStrategy
    extends FileChannelWithByteBufferStrategy {

  @Override
  protected ByteBufferFactory createByteBufferFactory(int bufferSize)
      throws IOException {
    return new AllocatedByteBufferFactory(bufferSize);
  }

  private class AllocatedByteBufferFactory extends ByteBufferFactory {
    public AllocatedByteBufferFactory(int bufferSize) throws IOException {
      super(bufferSize);

      this.byteBuffer = ByteBuffer.allocate(bufferSize);
    }

    @Override
    public byte[] getBytes() throws IOException {
      byte[] buffer = new byte[bufferSize];
      byteBuffer.get(buffer, 0, bufferSize);
      return buffer;
    }
  }

  @Override
  public String getDescription() {
    return "FileChannel with allocated ByteBuffer";
  }
}

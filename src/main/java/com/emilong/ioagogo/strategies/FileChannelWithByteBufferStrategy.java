package com.emilong.ioagogo.strategies;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
  * An IReadStrategy that reads the bytes from the given file
  * through a FileChannel with a ByteBuffer.
***/
public abstract class FileChannelWithByteBufferStrategy implements IReadStrategy {
  @Override
  public boolean supportsBufferSize(int bufferSize) {
    return true;
  }

  @Override
  public byte[] readBytes(int bufferSize, File inputFile) throws IOException {
    ByteBufferFactory factory = createByteBufferFactory(bufferSize);
    ByteBuffer byteBuffer = factory.getByteBuffer();

    FileInputStream inputStream = null;

    try {
      inputStream = new FileInputStream(inputFile);
      FileChannel fileChannel = inputStream.getChannel();

      for (int n = fileChannel.read(byteBuffer);
           n != -1;
           n = fileChannel.read(byteBuffer)) {
        byteBuffer.position(0);
      }
    } finally {
      if (inputStream != null) {
        inputStream.close();
      }
    }

    return factory.getBytes();
  }

  /**
    * Creates a new ByteBufferFactory appropriate for the given
    * subclass. This method should allocate a new factory for
    * each invocation to preserve thread safety.
    */
  protected abstract ByteBufferFactory createByteBufferFactory(int bufferSize)
    throws IOException;

  /**
    * A factory to create ByteBuffer and extract its array for
    * the readBytes() method.
    */
  protected abstract class ByteBufferFactory {
    protected final int bufferSize;
    protected ByteBuffer byteBuffer;

    public ByteBufferFactory(int bufferSize) throws IOException {
      this.bufferSize = bufferSize;
    }

    /**
      * Returns the allocated ByteBuffer of the given bufferSize.
      */
    public ByteBuffer getByteBuffer() throws IOException {
      return byteBuffer;
    }

    /**
      * Returns a byte array with the current contents of the ByteBuffer.
      */
    public abstract byte[] getBytes() throws IOException;
  }
}

package com.emilong.ioagogo.strategies;

import java.io.File;
import java.io.IOException;

public class FakeReadStrategy implements IReadStrategy {
  private final int maxBufferSize;

  public FakeReadStrategy() {
    this(Integer.MAX_VALUE);
  }

  public FakeReadStrategy(int maxBufferSize) {
    this.maxBufferSize = maxBufferSize;
  }

  @Override
  public boolean supportsBufferSize(int bufferSize) {
    return bufferSize < maxBufferSize;
  }

  @Override
  public byte[] readBytes(int bufferSize, File file) throws IOException {
    return new byte[bufferSize];
  }
}

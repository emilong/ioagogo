package com.emilong.ioagogo.strategies;

public interface IGogoStrategy {
  boolean supportsBufferSize(int bufferSize);

  String getDescription();
}

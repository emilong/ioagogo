package com.emilong.ioagogo.strategies;

public class FileInputStreamWithByteReadsStrategyTests
    extends SingleByteReadStrategyTestBase {

  @Override
  public IReadStrategy getStrategy() {
    return new FileInputStreamWithByteReadsStrategy();
  }
}

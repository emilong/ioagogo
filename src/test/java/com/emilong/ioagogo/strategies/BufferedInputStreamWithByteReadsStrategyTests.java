package com.emilong.ioagogo.strategies;

public class BufferedInputStreamWithByteReadsStrategyTests
  extends SingleByteReadStrategyTestBase {

  @Override
  public IReadStrategy getStrategy() {
    return new BufferedInputStreamWithByteReadsStrategy();
  }
}

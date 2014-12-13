package com.emilong.ioagogo.strategies;

public class RandomAccessFileWithByteReadsStrategyTests
  extends SingleByteReadStrategyTestBase {

  @Override
  public IReadStrategy getStrategy() {
    return new RandomAccessFileWithByteReadsStrategy();
  }
}

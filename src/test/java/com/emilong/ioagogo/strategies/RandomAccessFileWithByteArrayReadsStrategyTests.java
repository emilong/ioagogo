package com.emilong.ioagogo.strategies;

public class RandomAccessFileWithByteArrayReadsStrategyTests
    extends ArrayReadStrategyTestBase {

  @Override
  public IReadStrategy getStrategy() {
    return new RandomAccessFileWithByteArrayReadsStrategy();
  }
}

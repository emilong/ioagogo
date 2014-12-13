package com.emilong.ioagogo.strategies;

public class BufferedInputStreamWithByteArrayReadsStrategyTests
  extends ArrayReadStrategyTestBase {

  @Override
  public IReadStrategy getStrategy() {
    return new BufferedInputStreamWithByteArrayReadsStrategy();
  }
}

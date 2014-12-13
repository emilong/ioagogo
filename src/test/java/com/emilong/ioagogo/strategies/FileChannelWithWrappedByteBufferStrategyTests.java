package com.emilong.ioagogo.strategies;

public class FileChannelWithWrappedByteBufferStrategyTests
    extends ArrayReadStrategyTestBase {

  @Override
  public IReadStrategy getStrategy() {
    return new FileChannelWithWrappedByteBufferStrategy();
  }
}

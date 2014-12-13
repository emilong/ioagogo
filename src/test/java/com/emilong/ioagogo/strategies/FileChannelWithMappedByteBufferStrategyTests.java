package com.emilong.ioagogo.strategies;

public class FileChannelWithMappedByteBufferStrategyTests
    extends ArrayReadStrategyTestBase {

  @Override
  public IReadStrategy getStrategy() {
    return new FileChannelWithMappedByteBufferStrategy();
  }
}

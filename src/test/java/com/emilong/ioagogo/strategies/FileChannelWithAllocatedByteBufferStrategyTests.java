package com.emilong.ioagogo.strategies;

public class FileChannelWithAllocatedByteBufferStrategyTests
    extends ArrayReadStrategyTestBase {

  @Override
  public IReadStrategy getStrategy() {
    return new FileChannelWithAllocatedByteBufferStrategy();
  }
}

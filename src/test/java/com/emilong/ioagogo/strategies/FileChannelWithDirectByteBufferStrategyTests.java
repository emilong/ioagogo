package com.emilong.ioagogo.strategies;

public class FileChannelWithDirectByteBufferStrategyTests
  extends ArrayReadStrategyTestBase {

  @Override
  public IReadStrategy getStrategy() {
    return new FileChannelWithDirectByteBufferStrategy();
  }
}

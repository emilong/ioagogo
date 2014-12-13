package com.emilong.ioagogo.strategies;

public class FileInputStreamWithByteArrayReadsStrategyTests
  extends ArrayReadStrategyTestBase {

  @Override
  public IReadStrategy getStrategy() {
    return new FileInputStreamWithByteArrayReadsStrategy();
  }
}

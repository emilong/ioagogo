package com.emilong.ioagogo.strategies;

import static com.emilong.ioagogo.strategies.TaggedByteArrayMatcher.taggedWith;

import static org.hamcrest.CoreMatchers.equalTo;

import static org.junit.Assert.assertThat;

import org.junit.Test;

import java.io.IOException;

public abstract class ArrayReadStrategyTestBase extends ReadStrategyTestBase {
  @Test
  public void shouldSupportByteReadsOfAnySize() {
    int bufferSize = 1 + random.nextInt(ReadStrategyTemporaryFile.DEFAULT_SIZE);
    assertThat(getStrategy().supportsBufferSize(bufferSize), equalTo(true));
  }

  @Test
  public void shouldReadTheFileCorrectly() throws IOException {
    int bufferSize = 1 + random.nextInt(ReadStrategyTemporaryFile.DEFAULT_SIZE);
    byte[] readBytes = getStrategy().readBytes(bufferSize, inputFile);

    assertThat(readBytes.length, equalTo(bufferSize));
    assertThat(readBytes, taggedWith(readStrategyTemporaryFile.getValue()));
  }
}

package com.emilong.ioagogo.strategies;

import static com.emilong.ioagogo.strategies.TaggedByteArrayMatcher.taggedWith;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import java.io.IOException;

public abstract class SingleByteReadStrategyTestBase extends ReadStrategyTestBase {
  @Test
  public void shouldSupportOneByteReads() {
    assertThat(getStrategy().supportsBufferSize(1), equalTo(true));
  }

  @Test
  public void shouldNotSupportBufferSizesLargerThanOne() {
    assertThat(getStrategy().supportsBufferSize(2 + random.nextInt(1 << 16)), equalTo(false));
  }

  @Test
  public void shouldReadTheFileCorrectly() throws IOException {
    byte[] readBytes = getStrategy().readBytes(1, inputFile);

    assertThat(readBytes.length, equalTo(1));
    assertThat(readBytes, taggedWith(readStrategyTemporaryFile.getValue()));
  }
}

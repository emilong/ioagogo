package com.emilong.ioagogo.strategies;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.Random;

import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertThat;

import static com.emilong.ioagogo.strategies.ConstantByteArrayMatcher.containsOnly;

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
    assertThat(readBytes, containsOnly(readStrategyTemporaryFile.getValue()));
  }
}

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

public class BufferedInputStreamWithByteArrayReadsStrategyTests {
  @Rule
  public ReadStrategyTemporaryFile readStrategyTemporaryFile
    = new ReadStrategyTemporaryFile();

  private final Random random = new Random();
  private BufferedInputStreamWithByteArrayReadsStrategy strategy;
  private File inputFile;

  @Before
  public void setupStrategy() {
    strategy = new BufferedInputStreamWithByteArrayReadsStrategy();
    inputFile = readStrategyTemporaryFile.getInputFile();
  }

  @Test
  public void shouldSupportByteReadsOfAnySize() {
    int bufferSize = 1 + random.nextInt(ReadStrategyTemporaryFile.DEFAULT_SIZE);
    assertThat(strategy.supportsBufferSize(bufferSize), equalTo(true));
  }

  @Test
  public void shouldReadTheFileCorrectly() throws IOException {
    int bufferSize = 1 + random.nextInt(ReadStrategyTemporaryFile.DEFAULT_SIZE);
    byte[] readBytes = strategy.readBytes(bufferSize, inputFile);

    assertThat(readBytes.length, equalTo(bufferSize));
    assertThat(readBytes, containsOnly(readStrategyTemporaryFile.getValue()));
  }
}

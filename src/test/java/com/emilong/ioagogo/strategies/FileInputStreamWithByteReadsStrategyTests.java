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

public class FileInputStreamWithByteReadsStrategyTests {
  @Rule
  public ReadStrategyTemporaryFile readStrategyTemporaryFile
    = new ReadStrategyTemporaryFile();

  private final Random random = new Random();
  private FileInputStreamWithByteReadsStrategy strategy;
  private File inputFile;

  @Before
  public void setupStrategy() {
    strategy = new FileInputStreamWithByteReadsStrategy();
    inputFile = readStrategyTemporaryFile.getInputFile();
  }

  @Test
  public void shouldSupportOneByteReads() {
    assertThat(strategy.supportsBufferSize(1), equalTo(true));
  }

  @Test
  public void shouldNotSupportBufferSizesLargerThanOne() {
    assertThat(strategy.supportsBufferSize(2 + random.nextInt(1 << 16)), equalTo(false));
  }

  @Test
  public void shouldReadTheFileCorrectly() throws IOException {
    byte[] readBytes = strategy.readBytes(1, inputFile);

    assertThat((long) readBytes.length, equalTo(inputFile.length()));
    assertThat(readBytes, containsOnly(readStrategyTemporaryFile.getValue()));
  }
}

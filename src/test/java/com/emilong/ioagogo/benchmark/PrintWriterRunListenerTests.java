package com.emilong.ioagogo.benchmark;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import com.emilong.ioagogo.strategies.IReadStrategy;
import com.emilong.ioagogo.strategies.FakeReadStrategy;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import java.util.LinkedHashMap;
import java.util.Random;

public class PrintWriterRunListenerTests {
  private static final int MAX_BUFFER_SIZE = 1 << 16;

  private final Random random = new Random();

  private int bufferSize;
  private LinkedHashMap<IReadStrategy,TimerResult> results;
  private String expectedOutput;
  private StringWriter stringWriter;

  private PrintWriterRunListener printStreamRunListener;

  /**
    * Creates the results and appropriate result string.
    */
  @Before
  public void setupResults() {
    results = new LinkedHashMap<IReadStrategy,TimerResult>();

    bufferSize = 1 + random.nextInt(MAX_BUFFER_SIZE);
    long nanosElapsed = random.nextLong() & 0x7FFFFFFFFFL;
    TimerResult timerResult = new TimerResult(bufferSize, nanosElapsed);

    results.put(new FakeReadStrategy(), timerResult);
    results.put(new FakeReadStrategy(), null);

    expectedOutput = String.format("%d,%d,\n", bufferSize, nanosElapsed);
  }

  /**
    * Creates the PrintWriterRunListener and sets it output to the
    * stringWriter.
    */
  @Before
  public void setupRunListener() throws IOException {
    stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);

    printStreamRunListener = new PrintWriterRunListener(printWriter, ",");
  }

  @Test
  public void shouldOutputTheRightResults() throws IOException {
    printStreamRunListener.onRunResults(bufferSize, results);
    assertThat(stringWriter.toString(), equalTo(expectedOutput));
  }
}

package com.emilong.ioagogo.timers;

import com.emilong.ioagogo.strategies.IReadStrategy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.List;
import java.util.Random;

import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertThat;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.intThat;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SingleReadStrategyTimerTests {
  private final int MIN_RUNS = 2;
  private final int MAX_RUNS = 32;
  private final int MAX_BUFFER_INCREMENT = 1 << 16;

  private final Random random = new Random();

  @Mock
  private File file;

  @Mock
  private IFileFactory fileFactory;

  @Mock
  private IReadStrategy strategy;

  private SingleReadStrategyTimer timer;

  @Before
  public void setupStrategy() {
    MockitoAnnotations.initMocks(this);

    when(fileFactory.getFile()).thenReturn(file);
    when(strategy.supportsBufferSize(anyInt())).thenReturn(true);

    timer = new SingleReadStrategyTimer(fileFactory, strategy);
  }

  private int[] createBufferSizes() {
    int runCount = random.nextInt(MAX_RUNS - MIN_RUNS) + MIN_RUNS;

    int[] bufferSizes = new int[runCount];

    bufferSizes[0] = 1;
    for (int i = 1; i < bufferSizes.length; i++) {
      bufferSizes[i] = bufferSizes[i-1] +
                       random.nextInt(MAX_BUFFER_INCREMENT);
    }

    return bufferSizes;
  }

  @Test
  public void shouldPerformTheGivenNumberOfRuns() throws IOException {
    int[] bufferSizes = createBufferSizes();
    List<TimerResult> results = timer.performRuns(bufferSizes);

    assertThat(results.size(), equalTo(bufferSizes.length));
  }

  @Test
  public void shouldUseTheGivenBufferSizes() throws IOException {
    int[] bufferSizes = createBufferSizes();
    List<TimerResult> results = timer.performRuns(bufferSizes);

    int[] resultSizes = new int[results.size()];
    for (int i = 0; i < results.size(); i++) {
      resultSizes[i] = results.get(i).getBufferSize();
    }

    assertThat(resultSizes, equalTo(bufferSizes));
  }

  @Test
  public void shouldActuallyPerformTimings() throws IOException {
    int[] bufferSizes = createBufferSizes();
    List<TimerResult> results = timer.performRuns(bufferSizes);

    for (TimerResult result : results) {
      assertThat(result.getNanosElapsed(), greaterThan(0L));
    }
  }

  @Test
  public void shouldOnlyPerformRunsSupportedByTheStrategy() throws IOException {
    when(strategy.supportsBufferSize(1)).thenReturn(true);
    when(strategy.supportsBufferSize(intThat(greaterThan(1)))).thenReturn(false);

    int[] bufferSizes = createBufferSizes();
    List<TimerResult> results = timer.performRuns(bufferSizes);

    assertThat(results.size(), equalTo(1));
    assertThat(results.get(0).getBufferSize(), equalTo(1));
  }

  @Test
  public void shouldCallItsStrategyWithTheRightBufferSizes() throws IOException {
    int bufferSize = 1 + random.nextInt(MAX_BUFFER_INCREMENT);
    int[] bufferSizes = new int[] { bufferSize };

    timer.performRuns(bufferSizes);

    verify(strategy).readBytes(eq(bufferSize), any(File.class));
  }

  @Test
  public void shouldCallItsStrategyWithTheFileFromTheFileFactory() throws IOException {
    int[] bufferSizes = createBufferSizes();
    List<TimerResult> results = timer.performRuns(bufferSizes);

    verify(strategy, times(bufferSizes.length)).readBytes(anyInt(), eq(file));
  }
}

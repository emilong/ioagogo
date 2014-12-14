package com.emilong.ioagogo.benchmark;

import static org.hamcrest.Matchers.greaterThan;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.intThat;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.emilong.ioagogo.strategies.IReadStrategy;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.TypeSafeMatcher;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

public class MultipleReadStrategyTimerTests {
  private static final int MIN_RUNS = 2;
  private static final int MAX_RUNS = 32;
  private static final int MAX_BUFFER_INCREMENT = 1 << 16;

  private final Random random = new Random();

  @Mock
  private File file;

  @Mock
  private IFileFactory fileFactory;

  @Mock
  private IReadStrategy singleByteStrategy;

  @Mock
  private IReadStrategy arrayStrategy;

  @Mock
  private IRunListener runListener;

  private int[] bufferSizes;

  private List<IReadStrategy> readStrategies;

  private MultipleReadStrategyTimer timer;

  private void setupStrategyMocks() throws IOException {
    readStrategies = new ArrayList<IReadStrategy>();

    when(singleByteStrategy.supportsBufferSize(1)).thenReturn(true);
    when(singleByteStrategy.supportsBufferSize(intThat(greaterThan(1)))).thenReturn(false);
    readStrategies.add(singleByteStrategy);

    when(arrayStrategy.supportsBufferSize(anyInt())).thenReturn(true);
    readStrategies.add(arrayStrategy);
  }

  private void setupMocks() throws IOException {
    MockitoAnnotations.initMocks(this);

    setupStrategyMocks();

    when(fileFactory.getFile()).thenReturn(file);
  }

  private void createBufferSizes() {
    int runCount = random.nextInt(MAX_RUNS - MIN_RUNS) + MIN_RUNS;

    bufferSizes = new int[runCount];

    bufferSizes[0] = 1;
    for (int i = 1; i < bufferSizes.length; i++) {
      bufferSizes[i] = bufferSizes[i - 1] + random.nextInt(MAX_BUFFER_INCREMENT);
    }
  }

  /**
    * Performs all setup, including mocks and the timer itself. Invokes performRuns.
    */
  @Before
  public void setupTimer() throws IOException {
    setupMocks();
    timer = new MultipleReadStrategyTimer(fileFactory, readStrategies, runListener);
    createBufferSizes();

    timer.performRuns(bufferSizes);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void shouldCallTheIRunListenerForEachBufferSize() throws IOException {
    for (int i = 0; i < bufferSizes.length; i++) {
      verify(runListener).onRunResults(eq(bufferSizes[i]), any(LinkedHashMap.class));
    }
  }

  @Test
  public void shouldPerformAllRunsOnArrayStrategy() throws IOException {
    for (int i = 0; i < bufferSizes.length; i++) {
      verify(runListener)
          .onRunResults(eq(bufferSizes[i]),
                        argThat(hasResultFor(arrayStrategy).withBufferSize(bufferSizes[i])));
    }
  }

  @Test
  public void shouldPerformOnlyTheSingleBufferRunOnTheSingleByteStrategy() throws IOException {
    verify(runListener).onRunResults(eq(1),
                                     argThat(hasResultFor(singleByteStrategy).withBufferSize(1)));

    for (int i = 1; i < bufferSizes.length; i++) {
      verify(runListener).onRunResults(eq(bufferSizes[i]),
                                       argThat(doesNotHaveResultFor(singleByteStrategy)));
    }
  }

  @Test
  public void shouldCallTheStrategiesWithTheFileFromTheFileFactory() throws IOException {
    verify(singleByteStrategy, times(1)).readBytes(eq(1), eq(file));
    verify(arrayStrategy, times(bufferSizes.length)).readBytes(anyInt(), eq(file));
  }

  private class TimerResultForStrategyMatcher
      extends TypeSafeMatcher<LinkedHashMap<IReadStrategy,TimerResult>> {

    private final IReadStrategy strategy;
    private final boolean hasResult;
    private int bufferSize = -1;

    public TimerResultForStrategyMatcher(IReadStrategy strategy, boolean hasResult) {
      this.strategy = strategy;
      this.hasResult = hasResult;
    }

    public TimerResultForStrategyMatcher withBufferSize(int bufferSize) {
      this.bufferSize = bufferSize;
      return this;
    }

    @Override
    public boolean matchesSafely(LinkedHashMap<IReadStrategy,TimerResult> results) {
      TimerResult result = results.get(strategy);
      if (hasResult) {
        return result != null
          && result.getNanosElapsed() > 0
          && result.getBufferSize() == bufferSize;
      } else {
        return results.get(strategy) == null;
      }
    }

    @Override
    public void describeTo(Description description) {
      if (hasResult) {
        description.appendText("map does not contain an appropriate result for given strategy");
      } else {
        description.appendText("map contains a result for given strategy");
      }
    }
  }

  @Factory
  private TimerResultForStrategyMatcher hasResultFor(IReadStrategy strategy) {
    return new TimerResultForStrategyMatcher(strategy, true);
  }

  @Factory
  private TimerResultForStrategyMatcher doesNotHaveResultFor(IReadStrategy strategy) {
    return new TimerResultForStrategyMatcher(strategy, false);
  }
}

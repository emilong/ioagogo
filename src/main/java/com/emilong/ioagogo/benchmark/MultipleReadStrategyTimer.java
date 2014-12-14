package com.emilong.ioagogo.benchmark;

import com.emilong.ioagogo.strategies.IReadStrategy;

import java.io.File;
import java.io.IOException;

import java.util.LinkedHashMap;
import java.util.List;

public class MultipleReadStrategyTimer {
  private final IFileFactory fileFactory;
  private final List<IReadStrategy> readStrategies;
  private final IRunListener runListener;

  /**
    * Creates a new MultipleReadStrategyTimer with the given IFileFactory,
    * IReadStrategy's and IRunListener.
    */
  public MultipleReadStrategyTimer(IFileFactory fileFactory,
                                   List<IReadStrategy> readStrategies,
                                   IRunListener runListener) {
    this.fileFactory = fileFactory;
    this.readStrategies = readStrategies;
    this.runListener = runListener;
  }

  /**
    * Performs a run with all strategies for each of the given buffer
    * sizes. Performs at most one run per buffer size per strategy.
    * Will not perform any runs which a given strategy does not support.
    * Invokes onRunResults on the given IRunListener.
    */
  public void performRuns(int[] bufferSizes) throws IOException {
    File testFile = fileFactory.getFile();

    for (int bufferSize : bufferSizes) {
      LinkedHashMap<IReadStrategy,TimerResult> results
          = new LinkedHashMap<IReadStrategy,TimerResult>();

      for (IReadStrategy readStrategy : readStrategies) {
        TimerResult result = null;

        if (readStrategy.supportsBufferSize(bufferSize)) {
          long startNanos = System.nanoTime();
          readStrategy.readBytes(bufferSize, testFile);
          long nanosElapsed = System.nanoTime() - startNanos;

          result = new TimerResult(bufferSize, nanosElapsed);
        }

        results.put(readStrategy, result);
      }

      runListener.onRunResults(bufferSize, results);
    }
  }
}

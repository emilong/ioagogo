package com.emilong.ioagogo.benchmark;

import com.emilong.ioagogo.strategies.IReadStrategy;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

/**
 * IFileFactory returns a java.io.File to be used by
 * timers for performing test runs.
 */
public class SingleReadStrategyTimer {
  private final IFileFactory fileFactory;
  private final IReadStrategy readStrategy;

  public SingleReadStrategyTimer(IFileFactory fileFactory,
                                 IReadStrategy readStrategy) {
    this.fileFactory = fileFactory;
    this.readStrategy = readStrategy;
  }

  public List<TimerResult> performRuns(int[] bufferSizes) throws IOException {
    List<TimerResult> results = new ArrayList<TimerResult>(bufferSizes.length);

    for (int bufferSize : bufferSizes) {
      if (readStrategy.supportsBufferSize(bufferSize)) {
        File file = fileFactory.getFile();

        long start = System.nanoTime();
        readStrategy.readBytes(bufferSize, file);
        long elapsed = System.nanoTime() - start;

        results.add(new TimerResult(bufferSize, elapsed));
      }
    }

    return results;
  }
}

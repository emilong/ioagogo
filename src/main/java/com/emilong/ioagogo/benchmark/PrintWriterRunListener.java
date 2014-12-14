package com.emilong.ioagogo.benchmark;

import com.emilong.ioagogo.strategies.IReadStrategy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * PrintWriterRunListener writes the results of the MultipleReadStrategyTimer
 * out to the given PrintWriter, using the given separator.
 */
public class PrintWriterRunListener implements IRunListener {
  private final PrintWriter printWriter;
  private final String separator;

  /**
    * Creates a new PrintWriterRunListener with the given PrintWriter and
    * separator.
    */
  public PrintWriterRunListener(PrintWriter printWriter, String separator) {
    this.printWriter = printWriter;
    this.separator = separator;
  }

  @Override
  public void onRunResults(int bufferSize, LinkedHashMap<IReadStrategy, TimerResult> results) {
    StringBuilder builder = new StringBuilder();
    builder.append(bufferSize);

    for (Map.Entry<IReadStrategy,TimerResult> entry : results.entrySet()) {
      builder.append(separator);

      TimerResult timerResult = entry.getValue();

      if (timerResult != null) {
        builder.append(timerResult.getNanosElapsed());
      }
    }

    printWriter.println(builder.toString());
  }
}

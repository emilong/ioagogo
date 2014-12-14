package com.emilong.ioagogo.benchmark;

import com.emilong.ioagogo.strategies.IReadStrategy;

import java.util.LinkedHashMap;

/**
 * IRunListener receives the results of test runs.
 */
public interface IRunListener {
  void onRunResults(int bufferSize, LinkedHashMap<IReadStrategy, TimerResult> results);
}

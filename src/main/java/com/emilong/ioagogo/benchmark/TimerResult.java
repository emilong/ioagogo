package com.emilong.ioagogo.benchmark;

public class TimerResult {
  private final int bufferSize;
  private final long nanosElapsed;

  public TimerResult(int bufferSize, long nanosElapsed) {
    this.bufferSize = bufferSize;
    this.nanosElapsed = nanosElapsed;
  }

  public int getBufferSize() {
    return bufferSize;
  }

  public long getNanosElapsed() {
    return nanosElapsed;
  }
}

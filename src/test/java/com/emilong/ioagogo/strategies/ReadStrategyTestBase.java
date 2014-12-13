package com.emilong.ioagogo.strategies;

import org.junit.Before;
import org.junit.Rule;

import java.io.File;

import java.util.Random;

public abstract class ReadStrategyTestBase {
  @Rule
  public ReadStrategyTemporaryFile readStrategyTemporaryFile
      = new ReadStrategyTemporaryFile();

  protected final Random random = new Random();
  protected File inputFile;

  @Before
  public void setupInputFile() {
    inputFile = readStrategyTemporaryFile.getInputFile();
  }

  protected abstract IReadStrategy getStrategy();
}

package com.emilong.ioagogo.strategies;

import java.io.File;

import java.util.Random;

import org.junit.Before;
import org.junit.Rule;

public class ReadStrategyTestBase {
  @Rule
  public ReadStrategyTemporaryFile readStrategyTemporaryFile
    = new ReadStrategyTemporaryFile();

  protected final Random random = new Random();
  protected File inputFile;

  @Before
  public void setupInputFile() {
    inputFile = readStrategyTemporaryFile.getInputFile();
  }
}

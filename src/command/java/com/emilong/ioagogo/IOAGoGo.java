package com.emilong.ioagogo.benchmark;

import com.emilong.ioagogo.strategies.BufferedInputStreamWithByteArrayReadsStrategy;
import com.emilong.ioagogo.strategies.BufferedInputStreamWithByteReadsStrategy;
import com.emilong.ioagogo.strategies.FileChannelWithAllocatedByteBufferStrategy;
import com.emilong.ioagogo.strategies.FileChannelWithDirectByteBufferStrategy;
import com.emilong.ioagogo.strategies.FileChannelWithMappedByteBufferStrategy;
import com.emilong.ioagogo.strategies.FileChannelWithWrappedByteBufferStrategy;
import com.emilong.ioagogo.strategies.FileInputStreamWithByteArrayReadsStrategy;
import com.emilong.ioagogo.strategies.FileInputStreamWithByteReadsStrategy;
import com.emilong.ioagogo.strategies.IReadStrategy;
import com.emilong.ioagogo.strategies.RandomAccessFileWithByteArrayReadsStrategy;
import com.emilong.ioagogo.strategies.RandomAccessFileWithByteReadsStrategy;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;

public class IOAGoGo {
  // 128 MB
  public static final int DEFAULT_MAX_POWER = 27;

  // 256 MB
  public static final int DEFAULT_FILE_SIZE = 1 << 28;

  public static final String DEFAULT_SEPARATOR = "\t";

  private IRunListener runListener;
  private IFileFactory fileFactory;
  private List<IReadStrategy> readStrategies = new ArrayList<IReadStrategy>();
  private int[] bufferSizes;
  private String separator;

  private void createBufferSizes(int minPower, int maxPower) {
    bufferSizes = new int[maxPower - minPower + 1];

    for (int i = 0; i < bufferSizes.length; i++) {
      bufferSizes[i] = 1 << i + minPower;
    }
  }

  private Options setupOptions() {
    return new Options()
        .addOption("single", "single-byte", false,
                   "Enable single-byte read strategies (default: disabled)")
        .addOption("d", "directory", true,
                   "Sets the directory in which to perform the benchmark (default: current working directory)")
        .addOption("size", "file-size", true,
                   "Sets size of file to use for the benchmark (default: 256MB)")
        .addOption("min", "min-power", true,
                   "Sets the minimum power of 2 to use as a buffer size (default: 0, i.e. 1B)")
        .addOption("max", "max-power", true,
                   "Sets the maximum power of 2 to use as a buffer size (default: 27, i.e. 128MB)")
        .addOption("sep", "separator", true,
                   "Sets separator to use for the output (default: \\t)");
  }

  public IOAGoGo parseOptions(String[] args) throws IOException, ParseException {
    Options options = setupOptions();
    CommandLineParser parser = new GnuParser();

    try {
      CommandLine commandLine = parser.parse(options, args);

      setupStrategies(commandLine.hasOption("single"));

      File directory = new File(System.getProperty("user.dir"));

      if (commandLine.hasOption("d")) {
        directory = new File(commandLine.getOptionValue("d"));
      }

      int fileSize = DEFAULT_FILE_SIZE;

      if (commandLine.hasOption("size")) {
        fileSize = Integer.parseInt(commandLine.getOptionValue("size"));
      }

      fileFactory = new RandomFileFactory(fileSize, directory);

      int minPower = 0;
      int maxPower = DEFAULT_MAX_POWER;

      if (commandLine.hasOption("min")) {
        minPower = Integer.parseInt(commandLine.getOptionValue("min"));
      }

      if (commandLine.hasOption("max")) {
        maxPower = Integer.parseInt(commandLine.getOptionValue("max"));
      }

      createBufferSizes(minPower, maxPower);

      separator = commandLine.getOptionValue("sep", DEFAULT_SEPARATOR);
      runListener = new PrintWriterRunListener(new PrintWriter(System.out, true), separator);

      return this;
    } catch (ParseException e) {
      new HelpFormatter().printHelp(HelpFormatter.DEFAULT_SYNTAX_PREFIX, options);
      throw e;
    }
  }

  private void setupStrategies(boolean includeSingleByteStrategies) {
    if (includeSingleByteStrategies) {
      readStrategies.add(new FileInputStreamWithByteReadsStrategy());
    }

    readStrategies.add(new FileInputStreamWithByteArrayReadsStrategy());

    if (includeSingleByteStrategies) {
      readStrategies.add(new BufferedInputStreamWithByteReadsStrategy());
    }

    readStrategies.add(new BufferedInputStreamWithByteArrayReadsStrategy());

    if (includeSingleByteStrategies) {
      readStrategies.add(new RandomAccessFileWithByteReadsStrategy());
    }

    readStrategies.add(new RandomAccessFileWithByteArrayReadsStrategy());

    readStrategies.add(new FileChannelWithAllocatedByteBufferStrategy());
    readStrategies.add(new FileChannelWithWrappedByteBufferStrategy());
    readStrategies.add(new FileChannelWithDirectByteBufferStrategy());
    readStrategies.add(new FileChannelWithMappedByteBufferStrategy());
  }

  public void performRuns() throws IOException {
    // print the header
    System.out.print("Buffer size");
    for (int i = 0; i < readStrategies.size(); i++) {
      System.out.print(separator);
      System.out.print(readStrategies.get(i).getDescription());
    }
    System.out.println();

    try {
      MultipleReadStrategyTimer timer
          = new MultipleReadStrategyTimer(fileFactory, readStrategies, runListener);

      timer.performRuns(bufferSizes);
    } finally {
      if (fileFactory.getFile() != null) {
        fileFactory.getFile().delete();
      }
    }
  }

  public static void main(String[] args) throws Exception {
    new IOAGoGo().parseOptions(args).performRuns();
  }
}

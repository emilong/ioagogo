package com.emilong.ioagogo.benchmark;

import java.io.File;
import java.io.IOException;

/**
 * IFileFactory returns a java.io.File to be used by
 * timers for performing test runs.
 */
public interface IFileFactory {
  File getFile() throws IOException;
}

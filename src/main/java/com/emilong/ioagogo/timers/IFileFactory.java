package com.emilong.ioagogo.timers;

import java.io.File;

/**
 * IFileFactory returns a java.io.File to be used by
 * timers for performing test runs.
 */
public interface IFileFactory {
  File getFile();
}

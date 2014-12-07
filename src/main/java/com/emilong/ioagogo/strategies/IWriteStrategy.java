package com.emilong.ioagogo.strategies;

import java.io.File;
import java.io.IOException;

public interface IWriteStrategy extends IGogoStrategy {
  void writeBytes(int bufferSize, File outputFile) throws IOException;
}

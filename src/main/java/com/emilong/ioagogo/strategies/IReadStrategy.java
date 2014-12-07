package com.emilong.ioagogo.strategies;

import java.io.File;
import java.io.IOException;

/**
  * A strategy for reading bytes from a file.
***/
public interface IReadStrategy extends IGogoStrategy {
  /**
    * readBytes reads the entire given file into a byte array using
    * the given buffer size, if supported. Call {@link supportsBufferSize(int)}
    * before calling readBytes or this method may throw an
    * UnsupportedOperationException if the buffer size is in appropriate
    * for this strategy.
    * 
    * @param bufferSize the size of the buffer to use.
    * @param inputFile  the file to read from.
    * 
    * @return the byte array with the full contents of the given file.
    *
  ***/
  byte[] readBytes(int bufferSize, File inputFile) throws IOException;
}

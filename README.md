# IOAGoGo!

IOAGoGo is a library for doing basic IO benchmarking using various
different methodologies available in core Java. It's extensible and
should be usable anywhere that Java is.

# Command line interface

IOAGoGo includes a basic command line interface for benchmarking
the included strategies.

Build it with:

    ./gradlew commandJar

Run it with

    java -jar build/libs/ioagogo.jar

# Sample runs

The following runs are of a 256MB file.

## Mac OS X, with Oracle Java

Mac OSX 10.10.1, Mac Mini Server (Mid 2011), on a spinning, SATA disk,
with Oracle Java 1.8.0\_25-b17, 64-Bit server VM:

![Mac OSX 10.10.1, Mac Mini Server (Mid 2011), on a spinning, SATA disk,
with Oracle Java 1.8.0\_25-b17, 64-Bit server VM sample run](https://cdn.rawgit.com/emilong/ioagogo/master/samples/IOAGoGo-MacOSX-Java1.8.0.svg)

This run suggests that using a direct-allocated ByteBuffer with
FileChannel reads of at least 32KB, but no more than 2MB is your best
bet on this system.

## Android 4.4.4, Samsung Galaxy S4 (SGH-M919)

![Android 4.4.4, Samsung Galaxy S4 (SGH-M919)](https://cdn.rawgit.com/emilong/ioagogo/master/samples/Android-4.4.4-Samsung-SGH-M919.svg)

This run was conducted on the built-in storage of a US Samsung Galaxy
S4. It omits the "FileChannel with MappedByteBuffer" strategy as this
one ran out of memory quickly, failing with ENOMEM in a JNI call. The
other strategies failed to allocate 64MB and larger buffers, so those
are also omitted. This run suggests that all strategies are effectively
equivalent, so long as you use an 8KB buffer. There seem to be slight
benefits (~5%) in using a 4MB buffer. These runs showed fairly high
variability, so YMMV.

# Acknowledgements

Thanks to David Robert Nadeau, Ph.D., whose fine article,
[Java tip: How to read files quickly](http://nadeausoftware.com/articles/2008/02/java_tip_how_read_files_quickly),
inspired this library.

# License

Copyright (c) 2014-2015 Emil Ong

Permission is hereby granted, free of charge, to any person obtaining
a copy of this software and associated documentation files (the
"Software"), to deal in the Software without restriction, including
without limitation the rights to use, copy, modify, merge, publish,
distribute, sublicense, and/or sell copies of the Software, and to
permit persons to whom the Software is furnished to do so, subject
to the following conditions:

The above copyright notice and this permission notice shall be
included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR
ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

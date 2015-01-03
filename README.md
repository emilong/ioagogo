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

Mac OSX 10.10.1, Mac Mini Server (Mid 2011), on a spinning, SATA disk,
with Oracle Java 1.8.0\_25-b17, 64-Bit server VM:

![Mac OSX 10.10.1, Mac Mini Server (Mid 2011), on a spinning, SATA disk,
with Oracle Java 1.8.0\_25-b17, 64-Bit server VM sample run](samples/IOAGoGo-MacOSX-Java1.8.0.svg)

# Acknowledgements

Thanks to David Robert Nadeau, Ph.D., whose fine article
[Java tip: How to read files quickly](http://nadeausoftware.com/articles/2008/02/java_tip_how_read_files_quickly)inspired this library.

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

# JTheque Core #

A framework to create Java Modular applications.

## Building ##

You need a Java 6 (or newer) environment and Maven 2.0.9 (or newer) installed. You also need to have installed
this library into your maven repository :
[jtheque-unit-utils](http://github.com/wichtounet/jtheque-unit-utils "jtheque-unit-utils")

You should now be able to do a full build of `jtheque-unit-utils`:

    $ git clone git://github.com/wichtounet/jtheque-core.git
    $ cd jtheque-core
    $ mvn clean install

To use this library in your projects, add the following to the `dependencies` section of your `pom.xml`:

    <dependency>
       <groupId>org.jtheque</groupId>
       <artifactId>org.jtheque.core</artifactId>
       <version>2.1.0-SNAPSHOT</version>
    </dependency>

## Troubleshooting ##

Please consider using [Github issues tracker](http://github.com/wichtounet/jtheque-core/issues) to submit bug reports or feature requests.

## License ##

See `LICENSE` for details.
## [JVM Garbage Collector](https://www.quora.com/How-does-garbage-collection-work-in-the-JVM)

- Memory management is one of the most crucial part for any programming language.
- In the Java programming language, dynamic memory allocation of objects is achieved using the `new` operator.
- Once an object is created, it uses some memory and the memory remains allocated as long as there are references for the use of the object.
- When there are no references for an object, it is assumed to be no longer needed and the memory occupied by the object can be reclaimed.
- There is no explicit need to destroy an object as java handles the de-allocation automatically.
- In general, garbage means unreferenced objects.
- Garbage Collection is a way to destroy the unused objects.

### Important points about Java Garbage Collection

- Objects are created on the heap in Java.
- Garbage collection is a mechanism provided by Java Virtual Machine to reclaim heap space from objects which are eligible for Garbage collection.
- Garbage Collection in Java is carried by a daemon thread called Garbage Collector.
- Before removing an object from memory garbage collection thread invokes `finalize()` method of that object and gives an opportunity to perform any sort of cleanup required.
- Java programmer can not force garbage collection in Java; it will only trigger if JVM thinks it needs a garbage collection based on Java heap size.
- There are methods like `System.gc()` and `Runtime.gc()` which is used to send request of Garbage collection to JVM but it’s not guaranteed that garbage collection will happen.
- If there is no memory space for creating a new object in Heap, then JVM throws `java.lang.OutOfMemoryError` heap space.
- An object becomes eligible for Garbage collection if it's not reachable from any live threads or by any static references.
- Cyclic dependencies are not counted as the reference so if object A has a reference to object B and object B has a reference to Object A and they don't have any other live reference then both Objects A and B will be eligible for Garbage collection.

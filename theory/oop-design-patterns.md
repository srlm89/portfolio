## [Design Patterns](https://en.wikipedia.org/wiki/Design_pattern_(computer_science))

### Creational patterns

- Abstract factory
  - Provide an interface for creating families of related or dependent objects without specifying their concrete classes.
- Builder
  - Separate the construction of a complex object from its representation, allowing the same construction process to create various representations.
- Dependency Injection
  - A class accepts the objects it requires from an injector instead of creating the objects directly.
- Factory Method
  - Define an interface for creating a single object, but let subclasses decide which class to instantiate.
  - Factory Method lets a class defer instantiation to subclasses.
- Lazy initialization 
  - Tactic of delaying the creation of an object, the calculation of a value, or some other expensive process until the first time it is needed.
- Singleton
  - Ensure a class has only one instance, and provide a global point of access to it.


### Structural

- Adapter / Wrapper / Translator
  - Convert the interface of a class into another interface clients expect.
  - An adapter lets classes work together that could not otherwise because of incompatible interfaces.
- Composite
  - Compose objects into tree structures to represent part-whole hierarchies.
  - Composite lets clients treat individual objects and compositions of objects uniformly.
- Decorator
  - Attach additional responsibilities to an object dynamically keeping the same interface.
  - Decorators provide a flexible alternative to subclassing for extending functionality.
- Facade
  - Provide a unified interface to a set of interfaces in a subsystem.
  - Facade defines a higher-level interface that makes the subsystem easier to use.
- Flyweight
  - Use sharing to support large numbers of similar objects efficiently.
  - For instance, "string interning": storing only one copy of each distinct string value, which must be immutable.
- Proxy
  - Provide a surrogate or placeholder for another object to control access to it.

### Behavioral

- Command 
  - Encapsulate a request as an object, thereby allowing for the parameterization of clients with different requests, and the queuing or logging of requests.
  - It also allows for the support of undoable operations.
- Iterator
  - Provide a way to access the elements of an aggregate object sequentially without exposing its underlying representation.
- Null Object
  - Avoid null references by providing a default object.
- Observer / Publish-Subscribe:
  - Define a one-to-many dependency between objects where a state change in one object results in all its dependents being notified and updated automatically.
- State
  - Allow an object to alter its behavior when its internal state changes.
  - The object will appear to change its class.
- Strategy
  - Define a family of algorithms, encapsulate each one, and make them interchangeable.
  - Strategy lets the algorithm vary independently from clients that use it.
- Template Method
  - Define the skeleton of an algorithm in an operation, deferring some steps to subclasses.
  - Template method lets subclasses redefine certain steps of an algorithm without changing the algorithm's structure.
- Visitor
  - Represent an operation to be performed on the elements of an object structure.
  - Visitor lets a new operation be defined without changing the classes of the elements on which it operates.

### Concurrency

- Lock
  - One thread puts a "lock" on a resource, preventing other threads from accessing or modifying it.
- Monitor object
  - An object whose methods are subject to mutual exclusion, thus preventing multiple objects from erroneously trying to use it at the same time.
- Thread Pool
  - A number of threads are created to perform a number of tasks, which are usually organized in a queue.
  - Typically, there are many more tasks than threads.
  - Can be considered a special case of the object pool pattern.
- Thread-specific storage 
  - Static or "global" memory local to a thread.

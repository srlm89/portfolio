## [Cohesion vs Coupling](https://stackoverflow.com/questions/3085285/difference-between-cohesion-and-coupling)

- Good software design has high cohesion and low coupling.

### Cohesion

- Cohesion refers to what the class (or module) can do.
- Low cohesion would mean that the class does a great variety of actions -it is broad, unfocused on what it should do.
- High cohesion means that the class is focused on what it should be doing, i.e. only methods relating to the intention of the class.
- Cohesion is the indication of the relationship within a module.

### Coupling

- Coupling refers to how related or dependent two classes/modules are toward each other.
- For low coupled classes, changing something major in one class should not affect the other.
- High coupling would make it difficult to change and maintain your code; since classes are closely knit together, making a change could require an entire system revamp.
- Coupling is the indication of the relationships between modules.

## [SOLID](https://en.wikipedia.org/wiki/SOLID_(object-oriented_design))

- Single responsibility principle
  - A class should only have a single responsibility.
  - Only changes to one part of the software's specification should be able to affect the specification of the class.
- Open–closed principle
  - Software entities should be open for extension, but closed for modification.
- Liskov substitution principle
  - Objects in a program should be replaceable with instances of their subtypes without altering the correctness of that program.
- Interface segregation principle
  - Many client-specific interfaces are better than one general-purpose interface.
- Dependency inversion principle
  - One should depend upon abstractions, not concretions."

## [Domain-Driven Design](https://softwareengineering.stackexchange.com/questions/123023/what-is-domain-driven-development-in-practical-terms#123049)

- Domain-Driven Design aims to design software in high-value/high complexity domains.
- This turns into a different approach for building enterprise software: there's too much learning involved, and the most important consequence is that you won't get to the right solution at first shot.
  - Because you'll learn along the way.
  - Because stakeholders won't tell all the truth in a single shot.
  - Because the domain will evolve along the way.
- Most enterprise software has such poor separation of concerns that it's nearly untestable, and the software team lives in great fear of change because they have no idea what the side effects of ostensibly even trivial code changes might be, whereas a properly factored domain layer will be independently testable and verifiable.
- If you practice object-oriented design, including the discipline of loose coupling, and you practice unit testing fairly religiously, and you mercilessly refactor code, and you work with domain experts while building your system, essentially you'll end up with a result that's basically what advocates of domain driven design are talking about.
- DDD is a pragmatic approach to software development that can, over time, reduce the buildup of technical debt, and make your customers happier because you are able to speak the same language with each other, and deliver better-working solutions because of the advantages of understanding each other better.

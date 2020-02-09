## [Domain-Driven Design](https://softwareengineering.stackexchange.com/questions/123023/what-is-domain-driven-development-in-practical-terms#123049)

- Domain-Driven Design aims to design software in high-value/high complexity domains.
- This turns into a different approach for building enterprise software: there's too much learning involved, and the most important consequence is that you won't get to the right solution at first shot.
  - Because you'll learn along the way.
  - Because stakeholders won't tell all the truth in a single shot.
  - Because the domain will evolve along the way.
- Most enterprise software has such poor separation of concerns that it's nearly untestable, and the software team lives in great fear of change because they have no idea what the side effects of ostensibly even trivial code changes might be, whereas a properly factored domain layer will be independently testable and verifiable.
- If you practice object-oriented design, including the discipline of loose coupling, and you practice unit testing fairly religiously, and you mercilessly refactor code, and you work with domain experts while building your system, essentially you'll end up with a result that's basically what advocates of domain driven design are talking about.
- DDD is a pragmatic approach to software development that can, over time, reduce the buildup of technical debt, and make your customers happier because you are able to speak the same language with each other, and deliver better-working solutions because of the advantages of understanding each other better.

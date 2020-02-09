## [Patterns for Microservices — Sync vs. Async](https://dzone.com/articles/patterns-for-microservices-sync-vs-async)

### Microservices

- Microservices is an architecture paradigm where small and independent components work together as a system.
- This approach helps break down a complex system into manageable services.
- The services embrace micro-level concerns like single responsibility, separation of concerns, modularity, etc.
- Inter-service communication and execution flow is a foundational decision for a microservice architecture.
  - It can be synchronous or asynchronous in nature.

### Communication dimensions

- Consumers
  - Consumer applications often deal with the server synchronously and expect the interface to support that.
  - It is desirable for a consumer that the complexity of a distributed system is masked with a unified interface.
- Workflow Management
  - With many participating services, the management of a business-workflow is crucial.
    - It can be implicit and can happen at each service and therefore remain distributed across services.
    - Alternatively, an orchestrator service can own up the responsibility for orchestrating the business-flows.
    - A third option is an event-choreography based design. This substitutes an orchestrator via an event bus that each service binds to.
- Read/write frequency bias
  - A read-heavy system expects a majority of operations to complete synchronously.
  - A write-heavy system benefits from asynchronous execution.

### Synchronous communication

#### Synchronous communication definition

- Synchronous communication is a style of communication where the caller waits until a response is available.
- Synchronous communication is closely associated with HTTP protocol.
- The basic scheme is as follows:
  - An interceptor near the entry point intercepts the business flow request.
  - It then pushes the request to downstream services.
  - All the subsequent calls are synchronous in nature.
  - These calls can be parallel or sequential until processing is complete.

#### Synchronous communication variations

- De-Centralized and Synchronous
  - A de-centralized and synchronous communication style intercepts a flow at the entry point.
  - The interceptor forwards the request to the next step and awaits a response.
  - Since each request to the system can block services simultaneously, it is not ideal for a system with high read/write frequency.
- Orchestrated, Synchronous, and Sequential
  - A variation of synchronous communication is with a central orchestrator.
    - The orchestrator remains the intercepting service.
    - It processes the incoming request with workflow definition and forwards it to downstream services.
    - Each service, in turn, responds back to the orchestrator.
    - Until the processing of a request, orchestrator keeps making calls to services.
    - This burdens orchestrator more than other services.
  - The orchestrator is susceptible to being a single point of failure.
  - This style of architecture is still suitable for a read-heavy system.
- Orchestrated, Synchronous, and Parallel
  - Similar to previous approach but making independent requests parallel.
  - This leads to higher efficiency and performance.
  - With shorter response times, orchestrator can have a higher throughput.

#### Synchronous communication trade-offs

- Balanced capacity
  - A temporary burst at one component can flood other services with requests.
  - In asynchronous style, queues can mitigate temporary bursts.
  - Synchronous communication lacks this mediation and requires service capacity to match up during bursts.
  - Failing this, a cascading failure is possible.
  - Alternatively, resilience paradigms like circuit breakers can help mitigate a traffic burst in a synchronous system.
- Risk of Cascading Failures
  - Synchronous communication leaves upstream services susceptible to cascading failure in a microservices architecture.
  - If downstream services fail or worst yet, take too long to respond back, the resources can deplete quickly.
  - This can cause a domino effect for the system.
  - A possible mitigation strategy can involve consistent error handling, sensible timeouts for connections and enforcing SLAs.
- Increased Load Balancing & Service Discovery Overhead
  - The redundancy and availability needs for a participating service can be addressed by setting them up behind a load balancer.
  - This adds a level of indirection per service.
  - Additionally, each service needs to participate in a central service discovery setup.
- Coupling
  - Without abstractions in between, services bind directly to the contracts of the other services.
  - This develops a strong coupling over a period of time.
  - For simple changes in the contract, the owning service is forced to adopt versioning early on.
  
### Asynchronous communication

#### Asynchronous communication definition

- Asynchronous communication is well suited for a distributed architecture.
- It removes the need to wait for a response thereby decoupling the execution of two or more services.
- It is common to use a medium like a message bus that facilitates communication consistently across services.
- The asynchronous communication deals better with sporadic bursts of traffic.
- Each service in the architecture either produces messages, consumes messages or does both.

#### Asynchronous communication variations

- Choreographed Asynchronous Events
  - Each component listens to a central message bus and awaits an event.
  - The arrival of an event is a signal for execution.
  - Any context needed by execution is part of the event payload.
  - Triggering of downstream events is a responsibility that each service owns.
  - The architecture scales well for a write-heavy system.
  - The downside is that synchronous reads need mediation.
- Orchestrated, Asynchronous, and Sequential
  - Asynchronous communication with orchestrator at the center.
  - Each service is a producer and consumer to the central message bus.
  - Responsibilities of orchestrator involve routing messages to their corresponding services.
  - Each component consumes an incoming event or message and produces the response back on the message queue.
  - Orchestrator consumes this response and does transformation before routing ahead to next step.

#### Asynchronous communication trade-offs

- Queue
  - Only the message queue requires service discovery.
  - So the need for a central service discovery solution is less pressing.
  - Additionally, since multiple instances of a service are connected to a queue, external load balancing is not required.
  - This prevents another level of indirection that otherwise, load balancer introduces.
- Message Buses Are a Central Point of Failure
  - In the asynchronous communication style, message bus is the backbone of the system.
  - All services constantly produce-to and consume-from the message bus.
  - This makes the message bus the Achilles heel of the system as it remains a central point of failure.
- Higher System Complexity
  - Asynchronous systems tend to be significantly more complex than synchronous ones.
  - However, the complexity of system and demands of performance and scale are justified for the overhead.
  - Once adopted both orchestrator and individual components need to embrace the asynchronous execution.
- Reads/Queries Require Mediation
  - Asynchronous architecture needs mediation for synchronous reads/queries.
  - Simplest of all approaches is building a sync wrapper over an async system.
    - This is an entry point that can invoke asynchronous flows downstream.
    - At the same time, it holds the request awaiting until the response returns or a timeout occurs.
- Eventual Consistency
  - An asynchronous system can be eventually consistent.
  - It means that results in queries may not be latest, even though the system has issued the writes.

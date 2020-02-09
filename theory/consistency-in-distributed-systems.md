## [CAP Theorem (a.k.a. Brewer's Theorem)](https://dzone.com/articles/better-explaining-cap-theorem)

 - A distributed computer system cannot provide consistency, availability and partition tolerance, all at optimal levels.
   - Consistency: every read would get you the most recent write.
   - Availability: every node (if not failed) always executes queries.
   - Partition-tolerance: even if the connections between nodes are down, the other two (C & A) promises are kept. 
- We could look at a delay, a latency, of the update between nodes, as a temporary partitioning.
  - It will then cause a temporary decision between A and C:
    - On systems that lock all the nodes before allowing reads, we will get Consistency.
    - On systems that allow reads before updating all the nodes, we will get high Availability.
- Part of the idea of the CAP theorem regards the enforcement of two different data models:
  - ACID
    - A system that is not tolerant to network partitions can achieve data consistency and availability, and often does so by using transaction protocols.
  - Eventual consistency
    - In larger distributed-scale systems, network partitions are a given; therefore, consistency and availability cannot be achieved at the same time.

## [ACID](https://en.wikipedia.org/wiki/ACID_(computer_science))

- This property set enforces reliable data transactions.
- Atomicity guarantees that each transaction is treated as a single "unit", which either succeeds completely, or fails completely.
- Consistency ensures that a transaction can only bring the database from one valid state to another, maintaining database invariants.
- Isolation ensures that concurrent execution of transactions leaves the database in the same state that would have been obtained if the transactions were executed sequentially.
- Durability guarantees that once a transaction has been committed, it will remain committed even in the case of a system failure.

## [Eventual Consistency](https://www.allthingsdistributed.com/2008/12/eventually_consistent.html)

### Distributed systems requirements

- The idea of the CAP theorem has led to the popularity of models like Basically Available Soft State Services with Eventual Consistency.
- In this model, consistency is sacrificed for other priorities.
- Building reliable distributed systems at a worldwide scale demands trade-offs between consistency and availability.
  - The requirements placed on these infrastructure services are very strict.
  - They need to score high marks in the areas of security, scalability, availability, performance, and cost effectiveness.
  - And they need to meet these requirements while serving millions of customers around the globe, continuously.
- Given the worldwide scope of these systems, we use replication techniques ubiquitously to guarantee consistent performance and high availability.
- Although replication brings us closer to our goals, it cannot achieve them in a perfectly transparent manner.
- Under a number of conditions the customers of these services will be confronted with the consequences of using replication techniques inside the services.

### Consistency burden

- In an ideal world there would be only one consistency model: when an update is made all observers would see that update.
- This is not the case for globally distributed systems.
- This means that there are two choices on what to drop:
  - Making consistency a priority means that under certain conditions the system will not be available.
    - If the system emphasizes consistency, the developer has to deal with the fact that the system may not be available to take, for example, a write.
  - Relaxing consistency will allow the system to remain highly available under the partitionable conditions.
    - If the system emphasizes availability, it may always accept the write, but under certain conditions a read will not reflect the result of a recently completed write.

### Consistency types

- Strong consistency
  - After an update completes, any subsequent access will return the updated value.
- Weak consistency
  - The system does not guarantee that subsequent accesses will return the updated value.
- Eventual consistency
  - This is a specific form of weak consistency.
  - The storage system guarantees that if no new updates are made to the object, eventually all accesses will return the last updated value.

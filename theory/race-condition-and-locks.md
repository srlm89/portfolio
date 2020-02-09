## [Race Condition and Locks](https://support.microsoft.com/en-us/help/317723/description-of-race-conditions-and-deadlocks)

### Race Condition

- A race condition occurs when two threads access a shared variable at the same time.
  - The first thread reads the variable, and the second thread reads the same value from the variable.
  - Then the first thread and second thread perform their operations on the value, and they race to see which thread can write the value last to the shared variable.
  - The value of the thread that writes its value last is preserved, because the thread is writing over the value that the previous thread wrote.
- The most common symptom of a race condition is unpredictable values of variables that are shared between multiple threads.

### Locks

- To prevent the race conditions from occurring, you can lock shared variables, so that only one thread at a time has access to the shared variable.

### Deadlocks

- A deadlock occurs when two threads each lock a different variable at the same time and then try to lock the variable that the other thread already locked.
- As a result, each thread stops executing and waits for the other thread to release the variable.
- Because each thread is holding the variable that the other thread wants, nothing occurs, and the threads remain deadlocked.

### [Deadlock prevention](https://en.wikipedia.org/wiki/Dining_philosophers_problem)

- Partial order
  - This solution assigns a partial order to the resources.
  - And establishes the convention that all resources will be requested in order, and that no two resources unrelated by order will ever be used by a single unit of work at the same time. 
  - The solution is not always practical, especially when the list of required resources is not completely known in advance.
- Arbitrator
  - In order to pick up a resource, a thread must ask permission of the arbitrator.
  - The arbitrator can be implemented as a mutex.
  - This approach can result in reduced parallelism (other threads must wait for the thread that has the mutex even if there are other free resources available).

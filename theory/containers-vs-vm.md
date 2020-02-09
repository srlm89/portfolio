## [Containers vs Virtual Machines](https://docs.microsoft.com/en-us/virtualization/windowscontainers/about/containers-vs-vm)

### Containers

- A container is an isolated, lightweight silo for running an application on the host operating system.
- Containers build on top of the host operating system's kernel, and contain only apps and some lightweight operating system APIs and services that run in user mode.
- Containers themselves don't move; instead an orchestrator can automatically start or stop containers on cluster nodes to manage changes in load and availability.
- If a cluster node fails, any containers running on it are rapidly recreated by the orchestrator on another cluster node.

### Virtual Machines

- VMs run a complete operating system–including its own kernel.
- VMs require more system resources (CPU, memory, and storage).
- VMs can fail over to another server in a cluster, with the VM's operating system restarting on the new server.

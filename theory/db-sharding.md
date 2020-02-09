## [Database Sharding](https://stackoverflow.com/questions/992988/what-is-sharding-and-why-is-it-important)

- Sharding is just another name for "horizontal partitioning" of a database.
- Horizontal partitioning is a design principle whereby rows of a database table are held separately, rather than splitting by columns (as for normalization).
- Each partition forms part of a shard, which may in turn be located on a separate database server or physical location.
- The advantage is the number of rows in each table is reduced (this reduces index size, thus improves search performance).
- If the sharding is based on some real-world aspect of the data (e.g. European customers vs. American customers) then it may be possible to infer the appropriate shard membership easily and automatically, and query only the relevant shard.
- Each database shard server is identical, having the same table structure.
- Each complete data record exists in only one shard (unless there's mirroring for backup/redundancy) with all CRUD operations performed just in that database.
- Sharding represents a different way of organizing a logical database into smaller parts.

# Distributed Key-Value Store

A system design project for building a distributed key-value store.

## How it works

- There are 3 servers running on different ports - 8081, 8082, 8083.
- Make a POST request on any of the server with `/kv/put?key=<key>&value=<value>`.
- A GET request on any of the server should return the expected result, `/kv/get?key=<key>`.

## Feature yet to build

- Leader Selection with Raft algorithm
- Only Leader can perform WRITE operations
- Upon failure of the Leader node, a new Leader is elected.
- Fault tolerance, and syncing after restarting a faulty server.

## Resources

- https://github.com/jepsen-io/maelstrom/blob/main/doc/06-raft/01-key-value.md
- https://bytebytego.com/courses/system-design-interview/design-a-key-value-store
- https://thesecretlivesofdata.com/raft/
- https://nikasakana.medium.com/how-to-design-a-distributed-key-value-store-cfd83248541b

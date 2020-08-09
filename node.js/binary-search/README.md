# Binary search

## Implementation

- [binar-search.js](./binary-search.js)
- Note the usage of `Math.round()` because otherwise we would be indexing the array in nonexistent positions.

## Caveats

Note that to sort an array we must explictly implement an integer comparator:

```js
const array = [ -1, -10, -11, 1, 10, 11 ];
Array.from(array).sort();                // Produces: [ -1, -10, -11, 1, 10, 11 ]
Array.from(array).sort((a, b) => a - b); // Produces: [ -11, -10, -1, 1, 10, 11 ]
```

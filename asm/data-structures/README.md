### Sorted Data Structures

The aim of this exercise is to implement two sorted data structures: a Binary Search Tree (BST) and a sorted Double-Linked List (DLL). Both data structures have the same operations and the same node structure. They are the following:

```c
typedef struct node_t {
	short id;                           // bytes 0-1
	char *name;                         // bytes 2-5
	float score;                        // bytes 6-9
	struct nodo_t *pointer1;            // bytes 10-13
	struct nodo_t *pointer2;            // bytes 14-17
} __attribute__((__packed__)) node;     // 18 bytes

void insert(node** structure, char* name, short id, float score);
void remove_id(node** structure, short id);
void destroy(node* structure);
void print_in_order(node* structure, char *file);
```

In the case of the BST, `pointer1` and `pointer2` are the left and right subtrees of the node. For the DLL, `pointer1` and `pointer2` are the previous and next nodes.

Each node contains an `id`, a `name` and a `score` (in addition to its pointers), and it represents a movie rating. The goal is to fill the data structure with ratings extracted from a movie fan club database. The structure should be order by `id`.

Each operation should satisfy the following conditions:


#### Insert

BST:
* The operation should preserve the BST invariant.
* The tree is not required to be balanced, so the inserted node is always a leaf.
* If the BST already contains a node with the `id` equal to the node to be inserted, no insertion should take place.

DLL:
* The operation should preserve the order of the list, so the DLL must be sorted after the insertion.
* The DLL should not be circular, so the head and the tail of the list are not linked.
* If the DLL already contains a node with the `id` equal to the node to be inserted, no insertion should take place.


#### In-order Print

Traverse the data structure in ascending order and print the content of each node to an output file.


#### Remove

Remove the node with the specified `id` from the BST or the DLL, preserving the data structure invariants.


#### Destroy

Release every memory block used to create the data structure.


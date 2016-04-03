#ifndef __STRUCTURE_OPERATIONS_H__
#define __STRUCTURE_OPERATIONS_H__

typedef struct node_t {
	short id;
	char *name;
	float score;
	struct node_t* pointer1;
	struct node_t* pointer2;
} __attribute__((__packed__)) node;

void insert(node **data_structure, char* name, short id, float score);
void remove_id(node **data_structure, short id);
void print_in_order(node *data_structure, char *file);
void destroy(node* data_structure);

#endif /*__STRUCTURE_OPERATIONS_H__*/

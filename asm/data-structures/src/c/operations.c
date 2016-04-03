#include <stdlib.h>
#include <stdio.h>
#include <errno.h>
#include <string.h>
#include "operations.h"

char* read_arg(char* needle, char* haystack[], int count) {
	int i = 0;
	for (i = 0; i < count; i ++) {
		if (strcmp(needle, haystack[i]) == 0) {
			if (i < count -1) {
				return haystack[i+1];
			}	
		}
	}
	return 0;	
}

int is_valid_arg(char* needle, char* haystack[], int count) {
	int i = 0;
	for (i = 0; i < count; i ++) {
		if (strcmp(needle, haystack[i]) == 0) {
			return 1;
		}
	}
	return 0;	
}

int parse_action(char* action, node** data_structure) {
	char* token = strtok(action, " ");
	if (strncmp(token, "INSERT", 8) == 0) {
		token = strtok(NULL, " ");
		if (token == NULL) return -1;
		short id = atoi(token);
		token = strtok(NULL, " ");
		if (token == NULL) return -1;
		double score = atof(token);
		token = strtok(NULL, "\n");
		if (token == NULL) return -1;
		char* name = token;
		insert(data_structure, name, id, score); 
	} else if (strncmp(token, "REMOVE_ID", 8) == 0) {
		token = strtok(NULL, " \n");
		short id = atoi(token);
		if (token == NULL) return -1;
		remove_id(data_structure, id);
	} else if (strncmp(token, "PRINT_IN_ORDER", 8) == 0) {
		char* outfile = strtok(NULL, " \n");
		if (outfile == NULL) return -1;
		print_in_order(*data_structure, outfile);
	} else if (strncmp(token, "ERASE", 8) == 0) {
		char* outfile = strtok(NULL, " \n");
		if (outfile == NULL) return -1;
		FILE * file = fopen(outfile, "w");
		fclose(file);
	}
	
	return 0;
}

void show_usage(char* filename) {
	printf("Use: %s -f FILE\n", filename);
	printf("\n");
	printf("Each line in FILE describes a possible action.\n");
	printf("Possible actions are:\n");
	printf("-Element insertion: INSERT ID NAME SCORE\n");
	printf("\tInserts node with id ID, name NAME and score SCORE\n");
	printf("-Element removal: REMOVE_ID ID\n");
	printf("\tRemoves node with id ID\n");
	printf("-Print nodes: PRINT_IN_ORDER OUTPUT\n");
	printf("\tPrints all nodes ascendingly, ordered by id, to OUTPUT file\n");
}

int main (int argc, char* argv[]) {
	int result = 0;
	if (! is_valid_arg("-f", argv, argc)) {
		show_usage(argv[0]);
		return 0;
	}
	char * testfilename = read_arg("-f", argv, argc);
	FILE* filename = fopen(testfilename, "r");	
	if (filename == NULL) {
		printf("File cannot be open %#x %s\n", errno, strerror(errno));
		return 0;
	}
	node* data_structure = NULL;
	char c;
	char buf[4096];
	int len = 0;
	int line = 0;
	while ((c = fgetc(filename) ) != EOF) {
		buf[len++] = c;
		if (c == '\n'){
			buf[len] = 0;
			if (parse_action(buf, &data_structure) != 0) {
				printf("Invalid action in file %s (line %d)\n", testfilename, line);
				return 0;
			} 
			len = 0;
			line++;
		}
	}
	destroy(data_structure);
	fclose(filename);
	return result;
}


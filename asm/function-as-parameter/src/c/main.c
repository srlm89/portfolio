#include <stdio.h>

int the_first(int first, int second);
int the_second(int first, int second);

int choose_first(int first, int second);
int choose_second(int first, int second);

int choose(int (*function)(int, int), int first, int second);

int main(int argc, char** argv) {

    int fst = 1;
    int snd = 2;

    printf("The_first:          %d\n", the_first(fst, snd));
    printf("Choose_first:       %d\n", choose_first(fst, snd));
    printf("Choose_the_first:   %d\n", choose(&the_first, fst, snd));

    printf("The_second:         %d\n", the_second(fst, snd));
    printf("Choose_second:      %d\n", choose_second(fst, snd));
    printf("Choose_the_second:  %d\n", choose(&the_second, fst, snd));

    return 0;
}

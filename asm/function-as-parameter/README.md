### Function as Parameter

This source code shows how to pass a function as a parameter in ASM. Five functions are implemented in ASM:

```c
int the_first(int first, int second);
int the_second(int first, int second);
int choose(int (*f)(int, int), int first, int second);
int choose_first(int first, int second);
int choose_second(int first, int second);
```

* `the_first`: simply returns parameter `first`
* `the_second`: simply returns parameter `second`
* `choose`: simply invokes function from parameter `f` with arguments `first` and `second`
* `choose_first`: composition of `choose` with `the_first`
* `choose_second`: composition of `choose` with `the_second`


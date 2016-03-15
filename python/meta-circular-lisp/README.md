### Meta-Circular LISP

* This is a Python implementation of a LISP [meta-circular evaluator][1].
* This is a full-implementation of LISP language presented in [LISP 1.5 Manual][2].


### Features

This implementation is different from others that can be found on the Internet in two key aspects:

#### LISP data structure

It uses a data structure similar to LISP's original node-pointer structure. That is, every list is made up of pairs where the first element is the _car_ value of the list, and the second element is the _cdr_ of the list (which can be, in turn, another value or a pointer to another pair). In order to do this, a LISP parser was implemented to read in S-expressions. An algorithm to translate a binary object back to an S-expression was implemented as well to produce output.


#### Evaluator lookup hook

It is possible to change the LISP evaluator definition dynamically. The base evaluator is coded in Python, but there is a hook in the Python implementation so that it will try to look for the evaluator definition first in the _current program context_ (S-expressions defined by the user). This way, if the user chooses to code the evaluator with LISP's S-expressions during runtime, every S-expression will be interpreted using the user's evaluator. If the user definition is absent, then the base evaluator coded in Python is used.

To make this possible, the original implementation of the evaluator presented in the [LISP Manual][2] had to be extended so it can also interpret `define` and `lambda` both in `eval` and `apply` functions. Also, in order to manage the _current program context_ both in LISP and Python code, the data structure to represent it needed to be a LISP list containing LISP definitions.


### Comments

* Originally, this implementation tried to extend Peter Norvig's [Lispy][3], until it became clear that the extension could not be made. Lispy relies on Python data-structures and makes it impossible to mix in LISP definitions into the behaviour of the evaluator.

* Testing the [LISP Manual's][2] implementation of `apply` and `eval` in [Racket][4] was very useful in order to analyze and infer the expected behavior of the LISP evaluator. Doing this before starting the python implementation can definitely help to prevent wasting a lot of time.

* This project was inspired by the subject "Theory and Philosophy of Programming Languages" of UBA-FCEN, one of the non-mandatory subjects availabe to complete the M. Sc. in Computer Science. It was implemented by me and [Dardo Marasca][5].


[1]: https://en.wikipedia.org/wiki/Meta-circular_evaluator
[2]: http://www.softwarepreservation.org/projects/LISP/book/LISP%201.5%20Programmers%20Manual.pdf
[3]: http://norvig.com/lispy.html
[4]: https://racket-lang.org/
[5]: https://github.com/gefarion

### Demonstration

To see the LISP implementation in action you can try the following commands.

First, start the interpreter from the terminal with:

    ./meta_circular_lisp/run.py

You can notice in that file that a call to `sys.setrecursionlimit` is made. Without that call the python interpreter would fail during the following demonstration with the message "Maximum recursion depth exceeded."

Now run the following commands:

    ; Show current program context
    (context)

    ; Show the value of a definition from the context
    #t

    ; Define a constant
    (define complain (quote (I am late)))

    ; Show how the program context was extended
    (context)

    ; Show the value of the defined constant
    complain

    ; Reset the program context
    (reset)

    ; See how the program context was reset
    (context)

    ; Load our own implementation of a LISP evaluator in LISP
    (import (quote ./lisp/core.lisp))

    ; Show how the program context was extended (using the new evaluator)
    (context)

    ; Define a function (using the new evaluator)
    (define only-names (lambda (l) (cond ((null? l) (quote ())) (#t (cons (caar l) (only-names (cdr l)))))))

    ; Apply that function to see only the keys of the context definitions (using the new evaluator)
    (only-names (context))

    ; Define another function
    (define len (lambda (l) (cond ((null? l) 0) (#t (+ 1 (len (cdr l)))))))

    ; Apply that function to see the size of the program context
    (len (context))

    ; This constant was erased early, so we get an error message
    complain

    ; Define the constant
    (define complain (quote (I am cold)))

    ; Show the value of the defined constant
    complain

    ; Now, we try to define a function with two statements
    (define plusFour (lambda (x) (define four (+ 2 2)) (+ four x)))

    ; The application fails because multiline lambda definitions is not supported
    (plusFour 0)

    ; We load another evaluator which supports multiline lambda definitions
    (import (quote ./lisp/core-multiline.lisp))

    ; We can check that the function is still in the program context
    plusFour

    ; This time the application of the function is valid
    (plusFour 0)

    ; We define a function with three statements
    (define plusFive (lambda (x) (define suc (lambda (x) (+ x 1))) (define four 4) (suc (+ x four))))

    ; The application of the function is still valid
    (plusFive 0)

    ; Define a list of names
    (define names (quote (one two three)))

    ; We can see that those names are not defined
    one

    ; Define a function which defines new functions
    (define def* (lambda (l v z) (cond ((null? l) v) (#t (def* (cdr l) (+ v 1) (define (car l) v))))))

    ; Apply that function to the previously defined names
    (def* names 1 0)

    ; We can see that constant one was defined
    one

    ; We can see that constant two was defined
    two

    ; We can see that constant three was defined
    three

    ; Reset the program context
    (reset)

    ; See how the program context was reset
    (context)

    ; Exit
    (exit)


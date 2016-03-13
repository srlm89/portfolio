#lang racket/base

(require "core-test-lib.rkt")
(require rackunit rackunit/text-ui)

(define context (list
                 (cons 'A "A")
                 (cons 'B "B")
                 (cons 'one 1)
                 (cons 'two 2)
                 (cons 'x '(0))
                 (cons 'y '(8))
                 (cons 'suc '(lambda (e) (+ e 1)))
                 (cons 'if '(lambda (pred conseq alt) (cond (pred alt) (#t alt))))
                 (cons 'len '(lambda (l) (cond ((null? l) 0) (#t (+ (len (cdr l)) 1)))))
                )
)

(define basic-core-tests
  (test-suite "Tests for file core.rkt"

              (printf "# Testing helper functions~n")
              (test-case "testing helper *assoc"
                         (assocs 'A (cons 'A "A") context)
                         (assocs 'one (cons 'one 1) context)
                         (!assoc 'A "Fail when undefined symbol: A")
                         (!assoc 'C "Fail when undefined symbol: C" context)
              )
              (test-case "testing helper *pairlis"
                         (define n (length context))
                         (define extended (pairlis '(E) '(3.0) context))
                         (check-equal? (+ 1 n) (length extended))
                         (!assoc 'E "Fail when undefined symbol: E" context)
                         (assocs 'E (cons 'E 3.0) extended)
              )
              (test-case "testing helper *evcon"
                         (evcons '((#f 1) (#t 2)) 2)
                         (evcons '(((eq? (suc 1) one) one) (#t two)) 2 context)
              )
              (test-case "testing helper *evlis"
                         (evlis '(#t #f 2) '(#t #f 2))
                         (evlis '(one two x) '(1 2 (0)) context)
                         (evlis '(one two (car x) (cons A y)) '(1 2 0 ("A" 8)) context)
              )

              (printf "~n# Testing eval/apply~n")
              (test-case "testing atom and symbol"
                         (fails "x" "Fail when undefined symbol: x")
                         (evals "x" '(0) context)
              )
              (test-case "testing atom and not symbol"
                         (evals "0" 0)
                         (evals "#t" #t)
                         (evals "#f" #f)
                         (evals "'x" 'x)
              )
              (test-case "testing quote"
                         (evals "(quote 2)" 2)
                         (evals "(quote a b)" 'a)
                         (evals "(quote (1 2 3))" '(1 2 3))
                         (evals "(quote (1 2 3) (4 5))" '(1 2 3))
              )
              (test-case "testing cond"
                         (evals "(cond (#t 3))" 3)
                         (fails "(cond (#f 2))" "Fail when missing conditions")
              )
              (test-case "testing car"
                         (evals "(car '(1))" 1)
                         (evals "(car '(1) 5)" 1)
                         (evals "(car '(1 2))" 1)
                         (evals "(car '((1 2) 3))" '(1 2))
                         (evals "(car (cons 1 '(2 3)))" 1)
                         (fails "(car '(1) x)" "Fail when undefined symbol: x")
                         (fails "(car x)" "Fail when undefined symbol: x")
                         (evals "(car x)" 0 context)
              )
              (test-case "testing cdr"
                         (evals "(cdr '(1))" '())
                         (evals "(cdr '(1 2))" '(2))
                         (evals "(cdr '((1 2) 3))" '(3))
                         (evals "(cdr (cons 1 '(2 3)))" '(2 3))
                         (fails "(cdr x)" "Fail when undefined symbol: x")
              )
              (test-case "testing cons"
                         (evals "(cons 1 '(1))" '(1 1))
                         (evals "(cons '(1) '(1))" '((1) 1))
                         (evals "(cons 1 (cons 0 '('a)))" '(1 0 'a))
                         (fails "(cons x '())" "Fail when undefined symbol: x")
              )
              (test-case "testing +"
                         (evals "(+ 2 3)" 5)
                         (evals "(+ 2 3 4)" 5)
                         (fails "(+ 2)" "Fail when missing argument")
              )
              (test-case "testing atom?"
                         (evals "(atom? 1)" #t)
                         (evals "(atom? \"Hello\")" #t)
                         (evals "(atom? 'Hello)" #t)
                         (evals "(atom? '())" #f)
                         (evals "(atom? (cons 1 '()))" #f)
                         (evals "(atom? (car (cons 1 '())))" #t)
                         (evals "(atom? (cdr (cons 1 '())))" #f)
                         (fails "(atom? x)" "Fail when undefined symbol: x")
              )
              (test-case "testing eq?"
                         (evals "(eq? 0 0)" #t)
                         (evals "(eq? 0 1)" #f)
                         (evals "(eq? (+ 1 3) (+ 2 2))" #t)
                         (fails "(eq? x y)" "Fail when undefined symbol: x, y")
              )
              (test-case "testing null?"
                         (evals "(null? 1)" #f)
                         (evals "(null? 'symbol)" #f)
                         (evals "(null? '(1 2))" #f)
                         (evals "(null? '())" #t)
                         (fails "(null? x)" "Fail when undefined symbol: x")
              )
              (test-case "testing function"
                         (fails "(cddr '(1 2 3))" "Fail when undefined symbol: cddr")
                         (fails "(suc 1)" "Fail when undefined symbol: suc")
                         (evals "(suc 1)" 2 context)
                         (evals "(suc two)" 3 context)
                         (evals "(if (eq? one (car x)) 1 (cons B y))" '("B" 8) context)
                         (evals "(suc (len (cons (car x) y)))" 3 context)
              )
              (test-case "testing lambda"
                         (evals "(lambda () 2)" '(lambda () 2))
                         (evals "((lambda () 2))" 2)
                         (evals "((lambda (e l) (eq? (car l) e)) 'a '(a b))" #t)
                         (fails "((lambda (e) (inc e)) 3)" "Fail when undefined symbol: inc")
              )
              (test-case "testing context"
                         (evals "(context)" '())
                         (evals "(context)" '((2 . 1)) '((2 . 1)))
              )
              (test-case "testing label"
                         (evals "((label f-t (lambda () #t)))"  #t)
                         (evals "((label suc (lambda (e) (+ e 1))) 0)" 1)
                         (evals "((label len (lambda (l) (cond ((null? l) 0) (#t (+ (len (cdr l)) 1))))) '())" 0)
                         (evals "((label len (lambda (l) (cond ((null? l) 0) (#t (+ (len (cdr l)) 1))))) '(0 0))" 2)
                         (fails "((label f-f (dalamb () #f)))" "Fail when undefined symbol: dalamb")
              )
  )
)

(run-tests basic-core-tests)
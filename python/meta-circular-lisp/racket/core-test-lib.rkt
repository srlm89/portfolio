#lang racket

(provide evals fails assocs !assoc pairlis evcons evlis)

(require rackunit)
(require "core.rkt")

(define (pairlis list-a list-b [context '()])
  (define (log r) (printf "(*pairlis ~a ~a ~a)~n" list-a list-b (str-context context)))
  (define (call-pairlis) (*pairlis list-a list-b context))
  (call-and-log log call-pairlis))

(define (evcons list expected [context '()])
  (define (log r) (printf "(*evcon ~a ~a) => ~a~n" list (str-context context) r))
  (define (call-evcon) (*evcon list context))
  (check-equal? (call-and-log log call-evcon) expected))

(define (evlis list expected [context '()])
  (define (log r) (printf "(*evlis ~a ~a) => ~a~n" list (str-context context) r))
  (define (call-evlis) (*evlis list context))
  (check-equal? (call-and-log log call-evlis) expected))

(define (assocs to-assoc expected [context '()])
  (check-equal? (call-assoc to-assoc context) expected))

(define (!assoc to-assoc message [context '()])
  (check-exn exn:fail? (lambda () (call-assoc to-assoc context)) message))

(define (evals to-eval expected [context '()])
  (check-equal? (call-eval to-eval context) expected))

(define (fails to-eval message [context '()])
  (check-exn exn:fail? (lambda () (call-eval to-eval context)) message))

(define (call-assoc to-assoc context)
  (define (log r) (printf "(*assoc ~a ~a) => ~a~n" to-assoc (str-context context) r))
  (define (call-assoc) (*assoc to-assoc context))
  (call-and-log log call-assoc))

(define (call-eval to-eval context)
  (define parsed (parse to-eval))
  (define (log r) (printf "(*eval ~a ~a) => ~a~n" parsed (str-context context) r))
  (define (call-eval) (*eval parsed context))
  (call-and-log log call-eval))

(define (call-and-log logging call)
  (define (handle e) (logging (exn-message e)) e)
  (define (call-and-log) (define result (call)) (logging result) result)  
  (call-with-exception-handler handle call-and-log))

(define (str-context context)
  (cond ((null? context) "()") (#t "{ctx}")))

(define (parse str)
  (with-input-from-string str (lambda () (read))))
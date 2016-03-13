#lang racket
(provide atom? *eval *assoc *pairlis *evcon *evlis)

(define cadr (lambda (x) (car (cdr x))))

(define caar (lambda (x) (car (car x))))

(define cadar (lambda (x) (car (cdr (car x)))))

(define caddr (lambda (x) (car (cdr (cdr x)))))

(define (atom? x)
  (and (not (null? x))
       (not (pair? x))))

(define *pairlis (lambda (x y a)
                   (cond ((null? x) a)
                         (#t (cons (cons (car x) (car y))
                                   (*pairlis (cdr x) (cdr y) a))))))

(define *assoc (lambda (x a)
                 (cond ((null? a) (error "Undefined identifier: " x))
                       (#t (cond ((eq? (caar a) x) (car a))
                                 (#t (*assoc x (cdr a))))))))

(define *evcon (lambda (c a)
                 (cond ((null? c) (error "Exhausted every possible condition"))
                       ((*eval (caar c) a) (*eval (cadar c) a))
                       (#t (*evcon (cdr c) a)))))

(define *evlis (lambda (m a)
                 (cond ((null? m) (quote ()))
                       (#t (cons (*eval (car m) a) (*evlis (cdr m) a))))))

(define *apply (lambda (fn x a)
                 (cond ((atom? fn)
                        (cond ((eq? fn (quote car)) (caar x))
                              ((eq? fn (quote cdr)) (cdar x))                              
                              ((eq? fn (quote cons)) (cons (car x) (cadr x)))
                              ((eq? fn (quote +)) (+ (car x) (cadr x)))
                              ((eq? fn (quote atom?)) (atom? (car x)))
                              ((eq? fn (quote eq?)) (eq? (car x) (cadr x)))
                              ((eq? fn (quote null?)) (null? (car x)))
                              (#t (*apply (*eval fn a) x a))))
                       ((eq? (car fn) (quote lambda))
                        (*eval (caddr fn) (*pairlis (cadr fn) x a)))
                       ((eq? (car fn) (quote label))
                        (*apply (caddr fn) x (cons (cons (cadr fn) (caddr fn)) a)))
                       (#t (error "Unable to bind method: " fn)))))

(define *eval (lambda (e a)
                (cond ((atom? e)
                       (cond ((symbol? e) (cdr (*assoc e a)))
                             (#t e)))
                       ((atom? (car e))
                        (cond ((eq? (car e) (quote quote)) (cadr e))
                              ((eq? (car e) (quote cond)) (*evcon (cdr e) a))
                              ((eq? (car e) (quote lambda)) e)
                              ((eq? (car e) (quote context)) a)
                              (#t (*apply (car e) (*evlis (cdr e) a) a))))
                       (#t (*apply (car e) (*evlis (cdr e) a) a)))))

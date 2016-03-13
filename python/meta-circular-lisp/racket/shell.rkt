#lang racket

(require "core.rkt")
(require readline/readline)

(define (parse str)
  (with-input-from-string str (lambda () (read))))

(define (input-loop)
  (let loop ()
    (display "EVAL>")
    (define expr (read-line))
    (displayln (*eval (parse expr) '()))
    (loop)))

(input-loop)

#!/usr/bin/python -B

import meta_circular_lisp.parser as parser
import meta_circular_lisp.support as aux
import meta_circular_lisp.core as core
import unittest

class CoreTest(unittest.TestCase):

    ctx = aux.to_lisp_list([
        ['#t', True],
        ['#f', False],
        ['A', "A"],
        ['B', "B"],
        ['one', 1],
        ['two', 2],
        ['x', parser.mexpr('(0)')],
        ['y', parser.mexpr('(8)')],
        ['suc', parser.mexpr('(lambda (e) (+ e 1))')],
        ['if', parser.mexpr('(lambda (pred conseq alt) (cond (pred alt) (#t alt)))')],
        ['len', parser.mexpr('(lambda (l) (cond ((null? l) 0) (#t (+ (len (cdr l)) 1))))')]
    ])

    def test_atom_and_symbol(self):
        self.fails("x")
        self.evals("x", parser.mexpr('(0)'), CoreTest.ctx)

    def test_atom_and_not_symbol(self):
        self.evals("0", 0)
        self.evals("#t", True)
        self.evals("#f", False)
        self.evals("(quote x)", 'x')

    def test_quote(self):
        self.evals("(quote 2)", 2)
        self.evals("(quote a b)", 'a')
        self.evals("(quote (1 2 3))", parser.mexpr('(1 2 3)'))
        self.evals("(quote (1 2 3) (4 5))", parser.mexpr('(1 2 3)'))

    def test_cond(self):
        self.evals("(cond (#t 3))", 3)
        self.fails("(cond (#f 2))")

    def test_context(self):
        ctx = aux.execution_context
        self.evals("(context)", ctx)
        self.evals("(context)", ctx, [[2, 1], []])

    def test_error(self):
        escaped = lambda e: e.replace('(', '[(]').replace(')', '[)]')
        check = lambda e, f: self.assertRaisesRegexp(RuntimeError, escaped(e), self.evals, f, None)
        check('( ( Used invalid expression ) 1 )', "(error (quote (Used invalid expression)) 1)")
        check('( NullPointerException )', "(error (quote NullPointerException))")
        check('( S O S )', "(error (quote S) (quote O) (quote S))")

    def test_exit(self):
        self.assertRaises(EOFError, self.evals, "(exit)", None)

    def test_reset(self):
        ctx = aux.execution_context
        self.evals("(reset)", ctx, ctx)
        self.assertEqual(aux.base_context, ctx)

    def test_import(self):
        ctx = aux.execution_context
        get_len = lambda e: len(aux.to_plain_list(e))
        old_len = get_len(ctx)
        self.evals('(import (quote ../lisp/core.lisp))', '../lisp/core.lisp successfully loaded!', ctx)
        self.assertEqual(old_len + 11, get_len(ctx))

    def test_define(self):
        ctx = aux.execution_context
        names = lambda e: map(lambda f: f[0], aux.to_plain_list(e))
        self.assertFalse('plusTen' in names(ctx))
        self.assertFalse('oneTwoThree' in names(ctx))
        self.assertFalse('word' in names(ctx))
        self.evals('(define oneTwoThree 1.23)', 'oneTwoThree', ctx)
        self.evals('(define word (quote (Yes No)))', 'word', ctx)
        self.evals('(define plusTen (lambda (a) (+ a 10)))', 'plusTen', ctx)
        self.assertTrue('oneTwoThree' in names(ctx))
        self.assertTrue('plusTen' in names(ctx))
        self.evals('oneTwoThree', 1.23, ctx)
        self.evals('word', parser.mexpr('(Yes No)'), ctx)
        self.evals('(plusTen oneTwoThree)', 11.23, ctx)

    def test_car(self):
        self.evals("(car (quote (1)))", 1)
        self.evals("(car (quote (1)) 5)", 1)
        self.evals("(car (quote (1 2)))", 1)
        self.evals("(car (quote ((1 2) 3)))", parser.mexpr('(1 2)'))
        self.evals("(car (cons 1 (quote (2 3))))", 1)
        self.fails("(car (quote (1)) x)")
        self.fails("(car x)")
        self.evals("(car x)", 0, CoreTest.ctx)

    def test_cdr(self):
        self.evals("(cdr (quote (1)))", parser.mexpr('()'))
        self.evals("(cdr (quote (1 2)))", parser.mexpr('(2)'))
        self.evals("(cdr (quote ((1 2) 3)))", parser.mexpr('(3)'))
        self.evals("(cdr (cons 1 (quote (2 3))))", parser.mexpr('(2 3)'))
        self.fails("(cdr x)")
        self.evals("(cdr x)", parser.mexpr('()'), CoreTest.ctx)

    def test_cons(self):
        self.evals("(cons 1 (quote (1)))", parser.mexpr('(1 1)'))
        self.evals("(cons (quote (1)) (quote (1)))", parser.mexpr('((1) 1)'))
        self.evals("(cons 1 (cons 0 (quote (a))))", parser.mexpr('(1 0 a)'))
        self.fails("(cons x (quote ()))")
        self.evals("(cons x (quote ()))", parser.mexpr('((0))'), CoreTest.ctx)

    def test_plus(self):
        self.evals("(+ 2 3)", 5)
        self.evals("(+ 2 3 4)", 5)
        self.fails("(+ 2)")

    def test_atom(self):
        self.evals("(atom? 1)", True)
        self.evals("(atom? (quote Hello))", True)
        self.evals("(atom? (quote ()))", False)
        self.evals("(atom? (cons 1 (quote ())))", False)
        self.evals("(atom? (car (cons 1 (quote ()))))", True)
        self.evals("(atom? (cdr (cons 1 (quote ()))))", False)
        self.fails("(atom? x)")

    def test_eq(self):
        self.evals("(eq? 0 0)", True)
        self.evals("(eq? 0 1)", False)
        self.evals("(eq? (+ 1 3) (+ 2 2))", True)
        self.fails("(eq? x y)")

    def test_null(self):
        self.evals("(null? 1)", False)
        self.evals("(null? (quote symbol))", False)
        self.evals("(null? (quote (1 2)))", False)
        self.evals("(null? (quote ()))", True)
        self.fails("(null? x)")

    def test_function(self):
        self.fails("(cddr (quote (1 2 3)))")
        self.fails("(suc 1)")
        self.evals("(suc 1)", 2, CoreTest.ctx)
        self.evals("(suc two)", 3, CoreTest.ctx)
        self.evals("(if (eq? one (car x)) 1 (cons B y))", parser.mexpr('(B 8)'), CoreTest.ctx)
        self.evals("(suc (len (cons (car x) y)))", 3, CoreTest.ctx)

    def test_lambda(self):
        self.evals("(lambda () 2)", parser.mexpr('(lambda () 2)'))
        self.evals("((lambda () 2))", 2)
        self.evals("((lambda (e l) (eq? (car l) e)) (quote a) (quote (a b)))", True)
        self.fails("((lambda (e) (inc e)) 3)")

    def test_label(self):
        self.evals("((label f-t (lambda () #t)))",  True)
        self.evals("((label suc (lambda (e) (+ e 1))) 0)", 1)
        self.evals("((label len (lambda (l) (cond ((null? l) 0) (#t (+ (len (cdr l)) 1))))) (quote ()))", 0)
        self.evals("((label len (lambda (l) (cond ((null? l) 0) (#t (+ (len (cdr l)) 1))))) (quote (0 0)))", 2)
        self.fails("((label f-f (dalamb () #f)))")

    def test_skip_infinite_loop_for_numbers(self):
        self.evals("(1)", 1)
        self.evals("(2 3)", 2)
        self.evals("(9 5 7)", 9)

    def fails(self, input, context = aux.base_context):
        parsed = parser.mexpr(input)
        self.assertRaises(RuntimeError, core.eval, parsed, context)

    def evals(self, input, expected, context = aux.base_context):
        parsed = parser.mexpr(input)
        actual = core.eval(parsed, context)
        self.assertEquals(expected, actual)


if __name__ == '__main__':
    unittest.main()

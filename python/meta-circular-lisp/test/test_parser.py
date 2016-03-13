#!/usr/bin/python -B

import meta_circular_lisp.parser as parser
import unittest

class ParserTest(unittest.TestCase):

    def test_parses_atom(self):
        self.assertEqual(203, parser.atom('203'))
        self.assertEqual(2.5, parser.atom('2.5'))
        self.assertEqual(2e3, parser.atom('2e3'))
        self.assertEqual('symbol', parser.atom('symbol'))
        self.assertEqual('SYMBOL', parser.atom('SYMBOL'))

    def test_tokenizes_with_error_check(self):
        self.assertRaises(SyntaxError, parser.tokenize, '')
        self.assertRaises(SyntaxError, parser.tokenize, 'quote 2')
        self.assertRaises(SyntaxError, parser.tokenize, '((quote 2)')
        self.assertRaises(SyntaxError, parser.tokenize, '(quote (2)')
        self.assertRaises(SyntaxError, parser.tokenize, 'quote (2)')
        self.assertRaises(SyntaxError, parser.tokenize, 'quote (2))')
        self.assertRaises(SyntaxError, parser.tokenize, '(quote 2))')
        self.assertRaises(SyntaxError, parser.tokenize, '(quote) 2')
        self.assertRaises(SyntaxError, parser.tokenize, '. 1')
        self.assertRaises(SyntaxError, parser.tokenize, '(a . )')
        self.assertRaises(SyntaxError, parser.tokenize, '(. (a))')
        self.assertRaises(SyntaxError, parser.tokenize, '(a . (b) . )')

    def test_tokenization(self):
        self.assertEqual(parser.tokenize('2'), [2])
        self.assertEqual(parser.tokenize('.1'), [0.1])
        self.assertEqual(parser.tokenize('mxzy'), ['mxzy'])
        self.assertEqual(parser.tokenize(' (a A) '), ['(','a','A',')'])
        self.assertEqual(parser.tokenize(' (a A) '), ['(','a','A',')'])
        self.assertEqual(parser.tokenize('(1)'), ['(',1,')'])
        self.assertEqual(parser.tokenize('(  ( 1 )  )'), ['(','(',1,')',')'])
        self.assertEqual(parser.tokenize('(a)(b)'), ['(', 'a', ')','(','b',')'])
        self.assertEqual(parser.tokenize('(quote 2)'), ['(', 'quote', 2, ')'])

    def test_converts_to_mexpr(self):
        self.assertEqual(parser.mexpr('2'), 2)
        self.assertEqual(parser.mexpr('mxzy'), 'mxzy')
        self.assertEqual(parser.mexpr('()'), [])
        self.assertEqual(parser.mexpr('(2)'), [ 2, [] ])
        self.assertEqual(parser.mexpr('([])'), [ '[]', [] ])
        self.assertEqual(parser.mexpr('( 2 3 )'), [ 2, [ 3, [] ] ])
        self.assertEqual(parser.mexpr('( 2 3 4 )'), [ 2, [ 3, [ 4, [] ] ] ])
        self.assertEqual(parser.mexpr('(2 ( 3 ) 4)'), [ 2, [ [ 3, [] ], [ 4, [] ] ] ])
        self.assertEqual(parser.mexpr('( ( 2 3 ) 4)'), [ [ 2, [ 3, [] ] ], [ 4, [] ] ])
        self.assertEqual(parser.mexpr('( ( 2 3 (4) ) 5)'), [ [ 2, [ 3, [ [ 4, [] ], [] ] ] ], [ 5, [] ] ])
        self.assertEqual(parser.mexpr('( ( ( 1 2 ) ( 3 (4) 5 ) ((6)) ) )'), \
            [[[1, [2, []]], [[3, [[4, []], [5, []]]], [[[6, []], []], []]]], []])

    def test_converts_to_mexpr_using_dot(self):
        self.assertEqual(parser.mexpr('(.z)'), ['.z', []])
        self.assertEqual(parser.mexpr('(2.3)'), [ 2.3, [] ])
        self.assertEqual(parser.mexpr('(1 a.b)'), [1, ['a.b', []]])
        self.assertEqual(parser.mexpr('(.3 0)'), [0.3, [0, []]])
        self.assertEqual(parser.mexpr('(a . b)'), ['a', 'b'])
        self.assertEqual(parser.mexpr('(1 . (2))'), [1, [2, []]])
        self.assertEqual(parser.mexpr('(1 2 (3 . 4) 5)'), [1, [2, [[3, 4], [5, []]]]])
        self.assertEqual(parser.mexpr('(1 . (2 . (( 3 . 4) . (5))))'), [1, [2, [[3, 4], [5, []]]]])

    def test_converts_to_mexpr_using_dot_invalid(self):
        self.assertEqual(parser.mexpr('(1 . 2 . 3)'), [1, 2])
        self.assertEqual(parser.mexpr('(1 . (2 . 3 . 4) . 5)'), [1, [2, 3]])

    def test_converts_to_sexpr(self):
        self.assertEqual('1.0', parser.sexpr(1.0))
        self.assertEqual('( 1.0 )', parser.sexpr([1.0, []]))
        self.assertEqual('( a . b )', parser.sexpr(['a', 'b']))
        self.assertEqual('( 1 2 )', parser.sexpr([1, [2, []]]))
        self.assertEqual('( 1 ( ( 2 3 ) . 4 ) )', parser.sexpr([1, [[2, [3, []]], 4]]))
        self.assertEqual('( 1 2 3 . 4 )', parser.sexpr([1, [2, [3 , 4]]]))
        self.assertEqual('( 1 ( 2 ) 3 )', parser.sexpr([1, [[2, []], [3, []]]]))
        self.assertEqual('( 1 ( 2 ( 3 ( 4 ) 5 ) 6 7 ) )', \
            parser.sexpr([1, [[2, [[3, [[4, []], [5, []]]], [6, [7, []]]]], []]]))

    def test_sexpr_mexpr_back_and_forth(self):
        t1 = '( A )', ['A', []]
        t2 = '( ( A ) )', [['A', []], []]
        t3 = '( A B C )', ['A', [ 'B', [ 'C', [] ]]]
        t4 = '( A ( B . C ) )', ['A', [ ['B', 'C'], [] ]]
        t5 = '( ( A B ) C )', [['A', ['B', [] ]], [ 'C', [] ]]
        t6 = '( A B ( C D ) )', ['A', [ 'B', [[ 'C', [ 'D', [] ]], [] ]]]
        for s, m in [t1, t2, t3, t4, t5, t6]:
            self.assertEqual(m, parser.mexpr(s))
            self.assertEqual(m, parser.mexpr(parser.sexpr(m)))


if __name__ == '__main__':
    unittest.main()

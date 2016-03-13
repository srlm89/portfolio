#!/usr/bin/python -B

import meta_circular_lisp.support as execution
import unittest

class SupportTest(unittest.TestCase):

    def test_to_lisp_list(self):
        self.assertEqual([], execution.to_lisp_list([]))
        self.assertEqual([1, []], execution.to_lisp_list([1]))
        self.assertEqual([1, [2, []]], execution.to_lisp_list([1, 2]))
        self.assertEqual([1, [3, [4, []]]], execution.to_lisp_list([1, 3, 4]))

    def test_to_plain_list(self):
        self.assertEqual([], execution.to_plain_list([]))
        self.assertEqual([1], execution.to_plain_list([1, []]))
        self.assertEqual([1, 2], execution.to_plain_list([1, [2, []]]))
        self.assertEqual([1, 3, 4], execution.to_plain_list([1, [3, [4, []]]]))

    def test_clear_list(self):
        self.assertEqual(execution.base_context, execution.clear_list([]))
        self.assertEqual(execution.base_context, execution.clear_list([1]))
        self.assertEqual(execution.base_context, execution.clear_list([1, 2]))
        self.assertEqual(execution.base_context, execution.clear_list([1, [2, []]]))

    def test_add_definition(self):
        self.assertRaises(IndexError, execution.add_definition, 'one', 1, [])
        self.assertRaises(IndexError, execution.add_definition, 'one', 1, [0])
        context = [1, []]
        self.assertEqual('z', execution.add_definition('z', 0, context))
        self.assertEqual([['z', 0], [1, []]], context)


if __name__ == '__main__':
    unittest.main()

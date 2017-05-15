import unittest

import brackets.brackets as brackets


class BracketsTest(unittest.TestCase):

    def test_brackets_are_balanced(self):
        self.is_balanced('')
        self.is_balanced('abcd')
        self.is_balanced('()')
        self.is_balanced('([{<>}])')
        self.is_balanced('<()>[]')

    def test_brackets_are_not_balanced(self):
        self.not_balanced('([)]')
        self.not_balanced(')()')
        self.not_balanced('()(')

    def is_balanced(self, string):
        self.assertTrue(brackets.has_balanced_brackets(string))

    def not_balanced(self, string):
        self.assertFalse(brackets.has_balanced_brackets(string))


if __name__ == '__main__':
    unittest.main()

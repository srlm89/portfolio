import unittest

import stack.stack as stk


class StackTest(unittest.TestCase):

    def test_empty_throws_exception(self):
        stack = stk.Stack()
        self.assertRaisesRegexp(RuntimeError, "No such element: Empty Stack", stack.pop)

    def test_one_element(self):
        stack = stk.Stack()
        stack.push(1)
        self.check_with_list(stack, [1])

    def test_two_elements(self):
        stack = stk.Stack()
        stack.push(True)
        stack.push("prep")
        self.check_with_list(stack, ["prep", True])

    def test_three_elements(self):
        stack = stk.Stack()
        stack.push('_')
        stack.push(2.0)
        stack.push([0,1])
        self.check_with_list(stack, [[0,1], 2.0, '_'])

    def check_with_list(self, stack, expected):
        actual = []
        while True:
            try:
                actual.append(stack.pop())
            except RuntimeError as e:
                break
        self.assertEqual(expected, actual)

if __name__ == '__main__':
    unittest.main()

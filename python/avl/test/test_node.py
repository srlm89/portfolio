from avl.node import Node
import unittest

class NodeTest(unittest.TestCase):

    def test_initialization(self):
        node = self.new_node('x')
        self.check_node(node, 'x', 1, 0, 0, None, None, None)

    def test_change_value(self):
        node = self.new_node('x')
        node.change_value('a')
        self.check_node(node, 'a', 1, 0, 0, None, None, None)

    def test_link_left(self):
        node = self.new_node('x')
        left = self.new_node('l')
        node.link_l(left)
        self.check_node(node, 'x', 2, 1, 0, None, left, None)
        self.check_node(left, 'l', 1, 0, 0, node, None, None)

    def test_link_right(self):
        node = self.new_node('x')
        right  = self.new_node('r')
        node.link_r(right)
        self.check_node(node, 'x', 2, 0, 1, None, None, right)
        self.check_node(right, 'r', 1, 0, 0, node, None, None)

    def test_link_left_null(self):
        node = self.new_node('x')
        node.link_l(None)
        self.check_node(node, 'x', 1, 0, 0, None, None, None)

    def test_link_right_null(self):
        node = self.new_node('x')
        node.link_r(None)
        self.check_node(node, 'x', 1, 0, 0, None, None, None)

    def test_replace_child_left(self):
        node = self.new_node('x')
        left = self.new_node('l')
        left_2 = self.new_node('(L)')
        node.link_l(left)
        node.link_replace(left, left_2)
        self.check_node(node, 'x', 2, 1, 0, None, left_2, None)
        self.check_node(left_2, '(L)', 1, 0, 0, node, None, None)
        self.check_node(left, 'l', 1, 0, 0, None, None, None)

    def test_replace_child_right(self):
        node = self.new_node('x')
        right = self.new_node('r')
        right_2 = self.new_node('(R)')
        node.link_r(right)
        node.link_replace(right, right_2)
        self.check_node(node, 'x', 2, 0, 1, None, None, right_2)
        self.check_node(right_2, '(R)', 1, 0, 0, node, None, None)
        self.check_node(right, 'r', 1, 0, 0, None, None, None)

    def test_replace_child_left_null(self):
        node = self.new_node('x')
        left = self.new_node('l')
        node.link_l(left)
        node.link_replace(left, None)
        self.check_node(node, 'x', 1, 0, 0, None, None, None)
        self.check_node(left, 'l', 1, 0, 0, None, None, None)

    def test_replace_child_right_null(self):
        node = self.new_node('x')
        right = self.new_node('r')
        node.link_r(right)
        node.link_replace(right, None)
        self.check_node(node, 'x', 1, 0, 0, None, None, None)
        self.check_node(right, 'r', 1, 0, 0, None, None, None)

    def test_replace_child_left_fail(self):
        node = self.new_node('x')
        left = self.new_node('l')
        left_2 = self.new_node('(L)')
        node.link_l(left)
        node.link_replace(left_2, left_2)
        self.check_node(node, 'x', 2, 1, 0, None, left, None)
        self.check_node(left, 'l', 1, 0, 0, node, None, None)

    def test_replace_child_right_fail(self):
        node = self.new_node('x')
        right = self.new_node('r')
        right_2 = self.new_node('(R)')
        node.link_r(right)
        node.link_replace(right_2, right_2)
        self.check_node(node, 'x', 2, 0, 1, None, None, right)
        self.check_node(right, 'r', 1, 0, 0, node, None, None)
        self.check_node(right_2, '(R)', 1, 0, 0, None, None, None)

    def test_height_is_updated(self):
        g = self.new_node('grandpa')
        d = self.new_node('dad')
        s = self.new_node('son')
        g.link_r(d)
        d.link_r(s)
        self.check_node(s, 'son', 1, 0, 0, d, None, None)
        self.check_node(d, 'dad', 2, 0, 1, g, None, s)
        self.check_node(g, 'grandpa', 3, 0, 2, None, None, d)

    def test_height_is_updated_deep(self):
        a = self.new_node('h6')
        b = self.new_node('h5')
        c = self.new_node('h4')
        d = self.new_node('h3')
        e = self.new_node('h2')
        f = self.new_node('h1')
        a.link_l(b)
        b.link_l(c)
        c.link_l(d)
        d.link_l(e)
        e.link_l(f)
        self.check_node(f, 'h1', 1, 0, 0, e, None, None)
        self.check_node(e, 'h2', 2, 1, 0, d, f, None)
        self.check_node(d, 'h3', 3, 2, 0, c, e, None)
        self.check_node(c, 'h4', 4, 3, 0, b, d, None)
        self.check_node(b, 'h5', 5, 4, 0, a, c, None)
        self.check_node(a, 'h6', 6, 5, 0, None, b, None)

    def test_folded(self):
        a = self.new_node('a')
        b = self.new_node('b')
        c = self.new_node('c')
        d = self.new_node('d')
        e = self.new_node('e')
        f = self.new_node('f')
        self.check_fold(a, ['a', [], []])
        a.link_r(c)
        self.check_fold(a, ['a', [], ['c', [], []]])
        a.link_l(b)
        self.check_fold(a, ['a', ['b', [], []], ['c', [], []]])
        b.link_r(d)
        self.check_fold(a, ['a', ['b', [], ['d', [], []]], ['c', [], []]])
        c.link_l(e)
        self.check_fold(a, ['a', ['b', [], ['d', [], []]], ['c', ['e', [], []], []]])
        e.link_r(f)
        self.check_fold(a, ['a', ['b', [], ['d', [], []]], ['c', ['e', [], ['f', [], []]], []]])

    def test_flat(self):
        a = self.new_node('a')
        b = self.new_node('b')
        c = self.new_node('c')
        d = self.new_node('d')
        e = self.new_node('e')
        f = self.new_node('f')
        self.check_flat(a, ['a'])
        a.link_r(c)
        self.check_flat(a, ['a', 'c'])
        a.link_l(b)
        self.check_flat(a, ['a', 'b', 'c'])
        b.link_r(d)
        self.check_flat(a, ['a', 'b', 'd', 'c'])
        c.link_l(e)
        self.check_flat(a, ['a', 'b', 'd', 'c', 'e'])
        e.link_r(f)
        self.check_flat(a, ['a', 'b', 'd', 'c', 'e', 'f'])

    def check_node(self, node, v, h, h_l, h_r, p, l, r):
        self.assertEqual(v, node.value())
        self.assertEqual(h, node.height())
        self.assertEqual(l, node.left())
        self.assertEqual(r, node.right())
        self.assertEqual(p, node.parent())
        self.assertEqual(h_l, node.height_l())
        self.assertEqual(h_r, node.height_r())
        self.assertEqual(h_l - h_r, node.height_factor())

    def check_fold(self, node, expected):
        folded = node.folded()
        self.assertEqual(expected, folded)

    def check_flat(self, node, expected):
        flat = node.flat()
        self.assertEqual(expected, flat)

    def new_node(self, value):
        return Node(value)


if __name__ == '__main__':
    unittest.main()

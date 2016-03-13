from avl.avl import AVLTree
from avl.node import Node
import unittest

class AVLTreeTest(unittest.TestCase):

    def test_empty_tree(self):
        tree = self.new_avl()
        self.assertEqual(0, tree.size())
        self.assertEquals([], tree.flat())
        self.assertEquals([], tree.folded())
        self.assertFalse(tree.contains(0))
        self.assertEquals(None, tree.min())
        self.assertEqual(None, tree.max())

    def test_find_and_contains(self):
        tree = self.full_15()
        self.assertTrue(tree.contains(10))
        self.assertFalse(tree.contains(20))
        eight = tree.find(8)
        self.check_node_links(eight, None, 4, 12)
        ten = tree.find(10)
        self.check_node_links(ten, 12, 9, 11)
        self.assertEqual(None, tree.find(20))

    def test_replace_parent_null(self):
        tree = self.full_15()
        eight = tree.find(8)
        six = tree.find(6)
        self.assertEqual(None, tree.replace_parent(None, None))
        self.assertEqual(six, tree.replace_parent(None, six))
        self.check_node_links(six, 4, 5, 7)
        self.assertEqual(None, tree.replace_parent(six, None))
        self.check_node_links(six, None, 5, 7)
        self.assertEqual(None, tree.replace_parent(eight, None))
        self.check_node_links(eight, None, 4, 12)
        self.assertEqual(None, tree.root())

    def test_replace_parent_non_root(self):
        tree = self.full_15()
        ten = tree.find(10)
        two = tree.find(2)
        self.assertEqual(two, tree.replace_parent(ten, two))
        self.check_node_links(ten, None, 9, 11)
        self.check_node_links(two, 12, 1, 3)

    def test_replace_parent_root(self):
        tree = self.full_15()
        eight = tree.find(8)
        two = tree.find(2)
        self.assertEqual(two, tree.replace_parent(eight, two))
        self.check_node_links(eight, None, 4, 12)
        self.check_node_links(two, None, 1, 3)

    def test_rotate_right_non_root(self):
        tree = self.full_15()
        two = tree.find(2)
        four = tree.find(4)
        tree.rotate_right(four, two)
        self.check_node_links(two, 8, 1, 4)
        self.check_node_links(four, 2, 3, 6)

    def test_rotate_right_root(self):
        tree = self.full_15()
        four = tree.find(4)
        eight = tree.find(8)
        tree.rotate_right(eight, four)
        self.check_node_links(four, None, 2, 8)
        self.check_node_links(eight, 4, 6, 12)

    def test_rotate_left_non_root(self):
        tree = self.full_15()
        twelve = tree.find(12)
        fourteen = tree.find(14)
        tree.rotate_left(twelve, fourteen)
        self.check_node_links(fourteen, 8, 12, 15)
        self.check_node_links(twelve, 14, 10, 13)

    def test_rotate_left_root(self):
        tree = self.full_15()
        twelve = tree.find(12)
        eight = tree.find(8)
        tree.rotate_left(eight, twelve)
        self.check_node_links(twelve, None, 8, 14)
        self.check_node_links(eight, 12, 4, 10)

    def test_successor_of(self):
        tree = self.full_15()
        for i in range(1, 15):
            node = tree.find(i)
            succ = tree.successor_of(node)
            self.assertEquals(i + 1, succ.value())
        self.assertEquals(None, tree.successor_of(succ))

    def test_insert_balances_right_right(self):
        tree = self.new_avl()
        tree.insert(1)
        self.check_tree(tree, [1, [], []])
        tree.insert(2)
        self.check_tree(tree, [1, [], [2, [], []]])
        tree.insert(3)
        self.check_tree(tree, [2, [1, [], []], [3, [], []]])

    def test_insert_balances_right_left(self):
        tree = self.new_avl()
        tree.insert(1)
        self.check_tree(tree, [1, [], []])
        tree.insert(3)
        self.check_tree(tree, [1, [], [3, [], []]])
        tree.insert(2)
        self.check_tree(tree, [2, [1, [], []], [3, [], []]])

    def test_insert_balances_left_left(self):
        tree = self.new_avl()
        tree.insert(3)
        self.check_tree(tree, [3, [], []])
        tree.insert(2)
        self.check_tree(tree, [3, [2, [], []], []])
        tree.insert(1)
        self.check_tree(tree, [2, [1, [], []], [3, [], []]])

    def test_insert_balances_left_right(self):
        tree = self.new_avl()
        tree.insert(3)
        self.check_tree(tree, [3, [], []])
        tree.insert(1)
        self.check_tree(tree, [3, [1, [], []], []])
        tree.insert(2)
        self.check_tree(tree, [2, [1, [], []], [3, [], []]])

    def test_remove_balances_right_right(self):
        tree = self.new_avl()
        tree.insert(1)
        tree.insert(0)
        tree.insert(3)
        tree.insert(2)
        self.check_tree(tree, [1, [0, [], []], [3, [2, [], []], []]])
        tree.remove(0)
        self.check_tree(tree, [2, [1, [], []], [3, [], []]])

    def test_remove_balances_right_left(self):
        tree = self.new_avl()
        tree.insert(1)
        tree.insert(0)
        tree.insert(2)
        tree.insert(3)
        self.check_tree(tree, [1, [0, [], []], [2, [], [3, [], []]]])
        tree.remove(0)
        self.check_tree(tree, [2, [1, [], []], [3, [], []]])

    def test_remove_balances_left_right(self):
        tree = self.new_avl()
        tree.insert(3)
        tree.insert(1)
        tree.insert(4)
        tree.insert(2)
        self.check_tree(tree, [3, [1, [], [2, [], []]], [4, [], []]])
        tree.remove(4)
        self.check_tree(tree, [2, [1, [], []], [3, [], []]])

    def test_remove_balances_left_left(self):
        tree = self.new_avl()
        tree.insert(3)
        tree.insert(2)
        tree.insert(4)
        tree.insert(1)
        self.check_tree(tree, [3, [2, [1, [], []], []], [4, [], []]])
        tree.remove(4)
        self.check_tree(tree, [2, [1, [], []], [3, [], []]])

    def test_insert_15_easy(self):
        tree = self.new_avl()
        tree.insert_all([20, 4])
        self.check_tree(tree, [20, [4, [], []], []])
        tree.insert(15)
        self.check_tree(tree, [15, [4, [], []], [20, [], []]])

    def test_insert_8_easy(self):
        tree = self.new_avl()
        tree.insert_all([20, 4])
        self.check_tree(tree, [20, [4, [], []], []])
        tree.insert(8)
        self.check_tree(tree, [8, [4, [], []], [20, [], []]])

    def test_insert_15_normal(self):
        tree = self.new_avl()
        tree.insert_all([20, 4, 26, 3, 9])
        self.check_tree(tree, [20, [4, [3, [], []], [9, [], []]], [26, [], []]])
        tree.insert(15)
        self.check_tree(tree, [9, [4, [3, [], []], []], [20, [15, [], []], [26, [], []]]])

    def test_insert_8_normal(self):
        tree = self.new_avl()
        tree.insert_all([20, 4, 26, 3, 9])
        self.check_tree(tree, [20, [4, [3, [], []], [9, [], []]], [26, [], []]])
        tree.insert(8)
        self.check_tree(tree, [9, [4, [3, [], []], [8, [], []]], [20, [], [26, [], []]]])

    def test_insert_15_hard(self):
        tree = self.new_avl()
        tree.insert_all([20, 4, 26, 3, 9, 21, 30, 2, 7, 11])
        self.check_tree(tree, [20,
            [4, [3, [2, [], []], []], [9, [7, [], []], [11, [], []]]],
            [26, [21, [], []], [30, [], []]]
        ])
        tree.insert(15)
        self.check_tree(tree, [9,
            [4, [3, [2, [], []], []], [7, [], []]],
            [20, [11, [], [15, [], []]], [26, [21, [], []], [30, [], []]]]
        ])

    def test_insert_8_hard(self):
        tree = self.new_avl()
        tree.insert_all([20, 4, 26, 3, 9, 21, 30, 2, 7, 11])
        self.check_tree(tree, [20,
            [4, [3, [2, [], []], []], [9, [7, [], []], [11, [], []]]],
            [26, [21, [], []], [30, [], []]]
        ])
        tree.insert(8)
        self.check_tree(tree, [9,
            [4, [3, [2, [], []], []], [7, [], [8, [], []]]],
            [20, [11, [], []], [26, [21, [], []], [30, [], []]]]
        ])

    def test_remove_1_easy(self):
        tree = self.new_avl()
        tree.insert_all([2, 1, 4, 3, 5])
        self.check_tree(tree, [2, [1, [], []], [4, [3, [], []], [5, [], []]]])
        tree.remove(1)
        self.check_tree(tree, [4, [2, [], [3, [], []]], [5, [], []]])

    def test_remove_1_normal(self):
        tree = self.new_avl()
        tree.insert_all('629148B357ACD')
        self.check_tree(tree, ['6',
            ['2', ['1', [], []], ['4', ['3', [], []], ['5', [], []]]],
            ['9', ['8', ['7', [], []], []], ['B', ['A', [], []], ['C', [], ['D', [], []]]]]
        ])
        tree.remove('1')
        self.check_tree(tree, ['6',
            ['4', ['2', [], ['3', [], []]], ['5', [], []]],
            ['9', ['8', ['7', [], []], []], ['B', ['A', [], []], ['C', [], ['D', [], []]]]]
        ])

    def test_remove_1_hard(self):
        tree = self.new_avl()
        tree.insert_all('528137A469BC')
        self.check_tree(tree, ['5',
            ['2', ['1', [], []], ['3', [], ['4', [], []]]],
            ['8', ['7', ['6', [], []], []], ['A', ['9', [], []], ['B', [], ['C', [], []]]]]
        ])
        tree.remove('1')
        self.check_tree(tree, ['8',
            ['5', ['3', ['2', [], []], ['4', [], []]], ['7', ['6', [], []], []]],
            ['A', ['9', [], []], ['B', [], ['C', [], []]]]
        ])

    def test_remove_and_rotate_1(self):
        tree = self.new_avl()
        tree.insert_all('cbedfag')
        self.check_tree(tree, ['c',
            ['b', ['a', [], []], []],
            ['e', ['d', [], []], ['f', [], ['g', [], []]]]
        ])
        tree.remove('a')
        self.check_tree(tree, ['e',
            ['c', ['b', [], []], ['d', [], []]],
            ['f', [], ['g', [], []]]
        ])

    def test_remove_and_rotate_2(self):
        tree = self.new_avl()
        tree.insert_all('ecfbdga')
        self.check_tree(tree, ['e',
            ['c', ['b', ['a', [], []], []], ['d', [], []]],
            ['f', [], ['g', [], []]]
        ])
        tree.remove('g')
        self.check_tree(tree, ['c',
            ['b', ['a', [], []], []],
            ['e', ['d', [], []], ['f', [], []]]
        ])

    def test_remove_and_rotate_3(self):
        tree = self.new_avl()
        tree.insert_all('ecjadhkgilbf')
        self.check_tree(tree, ['e',
            ['c', ['a', [], ['b', [], []]], ['d', [], []]],
            ['j', ['h', ['g', ['f', [], []], []], ['i', [], []]], ['k', [], ['l', [], []]]]
        ])
        tree.remove('b')
        self.check_tree(tree, ['h',
            ['e', ['c', ['a', [], []], ['d', [], []]], ['g', ['f', [], []], []]],
            ['j', ['i', [], []], ['k', [], ['l', [], []]]]
        ])

    def test_remove_and_rotate_4(self):
        tree = self.new_avl()
        tree.insert_all('hckbeiladfjg')
        self.check_tree(tree, ['h',
            ['c', ['b', ['a', [], []], []], ['e', ['d', [], []], ['f', [], ['g', [], []]]]],
            ['k', ['i', [], ['j', [], []]], ['l', [], []]]
        ])
        tree.remove('j')
        self.check_tree(tree, ['e',
            ['c', ['b', ['a', [], []], []], ['d', [], []]],
            ['h', ['f', [], ['g', [], []]], ['k', ['i', [], []], ['l', [], []]]]
        ])

    def check_tree(self, tree, expected):
        flat = tree.flat()
        folded = tree.folded()
        self.assertEquals([], filter(lambda e: not tree.contains(e), flat))
        self.assertEquals(len(set(flat)), len(flat))
        self.assertEquals(len(flat), tree.size())
        self.assertEquals(max(flat), tree.max().value())
        self.assertEquals(min(flat), tree.min().value())
        self.check_tree_is_connected(tree)
        self.check_tree_is_balanced(tree)
        self.assertEquals(expected, folded)

    def check_tree_is_connected(self, tree):
        self.check_tree_rec(tree,
            lambda r: [[ None, r ]],
            lambda e: e[1] is not None,
            lambda n: self.assertEqual(n[0], n[1].parent()),
            lambda n, s: s.append([n[1], n[1].left()]) or s.append([n[1], n[1].right()])
        )

    def check_tree_is_balanced(self, tree):
        def check_factor_calculation(node):
            height_of = lambda n: 1 + max(height_of(n.left()), height_of(n.right())) if n else 0
            left_h = height_of(node.left())
            left_r = height_of(node.right())
            factor = node.height_factor()
            self.assertTrue(factor == left_h - left_r)
            self.assertTrue(abs(factor) < 2)
        self.check_tree_rec(tree,
            lambda r: [r],
            lambda e: e is not None,
            lambda n: check_factor_calculation(n),
            lambda n, s: s.append(n.left()) or s.append(n.right())
        )

    def check_tree_rec(self, tree, init, cond, assertion, appends):
        root = tree.root()
        stack = init(root)
        size = 0
        while len(stack) > 0:
            elem = stack.pop()
            if cond(elem):
                size += 1
                assertion(elem)
                appends(elem, stack)
        self.assertEqual(size, tree.size())

    def check_node_links(self, node, parent, left, right):
        self.assertEquals(left, node.left().value() if left else None)
        self.assertEquals(right, node.right().value() if right else None)
        self.assertEquals(parent, node.parent().value() if parent else None)

    def new_avl(self):
        return AVLTree()

    def new_node(self, value, left = None, right = None):
        node = Node(value)
        node.link_l(left)
        node.link_r(right)
        return node

    def full_15(self):
        root = self.new_node(
            8,
            self.new_node(
                4,
                self.new_node(2, self.new_node(1), self.new_node(3)),
                self.new_node(6, self.new_node(5), self.new_node(7))
            ),
            self.new_node(
                12,
                self.new_node(10, self.new_node(9), self.new_node(11)),
                self.new_node(14, self.new_node(13), self.new_node(15))
            )
        )
        self.assertEquals(root.folded(), [8,
            [4, [2, [1, [], []], [3, [], []]], [6, [5, [], []], [7, [], []]]],
            [12, [10, [9, [], []], [11, [] ,[]]], [14, [13, [], []], [15, [], []]]]
        ])
        tree = self.new_avl()
        tree.init_root(root)
        return tree


if __name__ == '__main__':
    unittest.main()

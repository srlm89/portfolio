import unittest

import substr_char.substr_char as sub


class SubstrCharTest(unittest.TestCase):

    def test_shortest_full_text_if_no_solution(self):
        self.assertEqual('a', sub.shortest_diff_chars('a', 0))
        self.assertEqual('a', sub.shortest_diff_chars('a', 2))
        self.assertEqual('babc', sub.shortest_diff_chars('babc', 4))

    def test_shortest_substring_with_k_chars(self):
        self.assertEqual('a', sub.shortest_diff_chars('aabbccdd', 1))
        self.assertEqual('ab', sub.shortest_diff_chars('aabbccdd', 2))
        self.assertEqual('abbc', sub.shortest_diff_chars('aabbccdd', 3))
        self.assertEqual('b', sub.shortest_diff_chars('babc', 1))
        self.assertEqual('ba', sub.shortest_diff_chars('babc', 2))
        self.assertEqual('ab', sub.shortest_diff_chars('abccba', 2))
        self.assertEqual('abc', sub.shortest_diff_chars('babc', 3))
        self.assertEqual('bac', sub.shortest_diff_chars('babac', 3))
        self.assertEqual('acb', sub.shortest_diff_chars('baacba', 3))
        self.assertEqual('cba', sub.shortest_diff_chars('baaaccba', 3))
        self.assertEqual('accb', sub.shortest_diff_chars('baaaccbba', 3))
        self.assertEqual('bac', sub.shortest_diff_chars('aabacbebebe', 3))
        self.assertEqual('za', sub.shortest_diff_chars('zaacbbccd', 2))
        self.assertEqual('abc', sub.shortest_diff_chars('abccccccccaaddddeeee', 3))

    def test_longest_first_char_if_no_solution(self):
        self.assertEqual('a', sub.longest_diff_chars('a', 0))
        self.assertEqual('a', sub.longest_diff_chars('a', 2))
        self.assertEqual('b', sub.longest_diff_chars('babc', 4))

    def test_longest_substring_with_k_chars(self):
        self.assertEqual('aa', sub.longest_diff_chars('aabbccdd', 1))
        self.assertEqual('aabb', sub.longest_diff_chars('aabbccdd', 2))
        self.assertEqual('aabbcc', sub.longest_diff_chars('aabbccdd', 3))
        self.assertEqual('b', sub.longest_diff_chars('babc', 1))
        self.assertEqual('bab', sub.longest_diff_chars('babc', 2))
        self.assertEqual('bccb', sub.longest_diff_chars('abccba', 2))
        self.assertEqual('babc', sub.longest_diff_chars('babc', 3))
        self.assertEqual('babac', sub.longest_diff_chars('babac', 3))
        self.assertEqual('baacba', sub.longest_diff_chars('baacba', 3))
        self.assertEqual('baaaccba', sub.longest_diff_chars('baaaccba', 3))
        self.assertEqual('baaaccbba', sub.longest_diff_chars('baaaccbba', 3))
        self.assertEqual('cbebebe', sub.longest_diff_chars('aabacbebebe', 3))
        self.assertEqual('cbbcc', sub.longest_diff_chars('zaacbbccd', 2))
        self.assertEqual('ccccccccaadddd', sub.longest_diff_chars('abccccccccaaddddeeee', 3))


if __name__ == '__main__':
    unittest.main()

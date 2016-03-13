from test_sort_int import IntSortTest
from sorting.quick_sort import sort
from sorting.quick_sort import partition
import unittest

class QuickSortTest(IntSortTest):

    def test_partition_no_repetitions(self):
        self.do_partition(0, [1], [1], 0)
        self.do_partition(0, [1, 2], [1, 2], 0)
        self.do_partition(1, [1, 2], [1, 2], 1)
        self.do_partition(0, [1, 2], [2, 1], 1)
        self.do_partition(1, [1, 2], [2, 1], 0)
        self.do_partition(0, [1, 3, 2], [2, 1, 3], 1)
        self.do_partition(1, [1, 2, 3], [2, 1, 3], 0)
        self.do_partition(2, [1, 2, 3], [2, 1, 3], 2)
        self.do_partition(0, [1, 2, 3, 4], [2, 4, 1, 3], 2)
        self.do_partition(1, [1, 2, 3, 4], [2, 4, 1, 3], 0)
        self.do_partition(2, [2, 1, 3, 4], [2, 4, 1, 3], 3)
        self.do_partition(3, [2, 1, 3, 4], [2, 4, 1, 3], 1)

    def test_partition_with_repetitions(self):
        self.do_partition(0, [0, 0], [0, 0], 0)
        self.do_partition(0, [0, 1, 0], [0, 0, 1], 0)
        self.do_partition(2, [0, 0, 1], [0, 0, 1], 2)
        self.do_partition(0, [0, 0, 1], [0, 1, 0], 0)
        self.do_partition(2, [0, 0, 1], [0, 1, 0], 1)
        self.do_partition(0, [0, 0, 1], [1, 0, 0], 1)
        self.do_partition(2, [0, 0, 1], [1, 0, 0], 0)
        self.do_partition(0, [0, 1, 2, 0], [0, 0, 1, 2], 0)
        self.do_partition(2, [0, 0, 1, 2], [0, 0, 1, 2], 2)
        self.do_partition(3, [0, 1, 0, 2], [0, 0, 1, 2], 3)
        self.do_partition(0, [0, 2, 1, 0], [0, 0, 2, 1], 0)
        self.do_partition(2, [0, 0, 1, 2], [0, 0, 2, 1], 3)
        self.do_partition(3, [0, 0, 1, 2], [0, 0, 2, 1], 2)
        self.do_partition(0, [0, 1, 0, 2], [1, 2, 0, 0], 2)
        self.do_partition(2, [0, 0, 1, 2], [1, 2, 0, 0], 0)
        self.do_partition(3, [1, 0, 0, 2], [1, 2, 0, 0], 1)
        self.do_partition(0, [0, 2, 0, 1], [2, 1, 0, 0], 2)
        self.do_partition(2, [0, 0, 1, 2], [2, 1, 0, 0], 1)
        self.do_partition(3, [1, 0, 0, 2], [2, 1, 0, 0], 0)
        self.do_partition(0, [0, 0, 2, 1], [1, 0, 0, 2], 1)
        self.do_partition(2, [0, 0, 1, 2], [1, 0, 0, 2], 0)
        self.do_partition(3, [0, 0, 1, 2], [1, 0, 0, 2], 3)
        self.do_partition(0, [0, 0, 1, 2], [2, 0, 0, 1], 1)
        self.do_partition(2, [0, 0, 1, 2], [2, 0, 0, 1], 3)
        self.do_partition(3, [0, 0, 1, 2], [2, 0, 0, 1], 0)
        self.do_partition(0, [0, 0, 2, 1], [0, 1, 0, 2], 0)
        self.do_partition(2, [0, 0, 1, 2], [0, 1, 0, 2], 1)
        self.do_partition(3, [1, 0, 0, 2], [0, 1, 0, 2], 3)
        self.do_partition(0, [0, 0, 1, 2], [0, 2, 0, 1], 0)
        self.do_partition(2, [0, 0, 1, 2], [0, 2, 0, 1], 3)
        self.do_partition(3, [0, 0, 1, 2], [0, 2, 0, 1], 1)
        self.do_partition(0, [0, 2, 0, 1], [1, 0, 2, 0], 1)
        self.do_partition(2, [0, 0, 1, 2], [1, 0, 2, 0], 0)
        self.do_partition(3, [0, 1, 0, 2], [1, 0, 2, 0], 2)
        self.do_partition(0, [0, 1, 0, 2], [2, 0, 1, 0], 1)
        self.do_partition(2, [0, 0, 1, 2], [2, 0, 1, 0], 2)
        self.do_partition(3, [0, 1, 0, 2], [2, 0, 1, 0], 0)

    def do_partition(self, pos, expected, array, pivot):
        copy = list(array)
        result = partition(copy, 0, len(array) - 1, pivot)
        self.assertEqual(expected, copy)
        self.assertEqual(pos, result)

    # override
    def do_sort(self, array):
        return sort(array)


if __name__ == '__main__':
    unittest.main()

from test_sort_int import IntSortTest
from sorting.count_sort import sort
import unittest

class CountSortTest(IntSortTest):

    # override
    def do_sort(self, array):
        return sort(array)


if __name__ == '__main__':
    unittest.main()
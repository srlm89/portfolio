from test_sort_int import IntSortTest
from sorting.heap_sort import sort
import unittest

class HeapSortTest(IntSortTest):

    # override
    def do_sort(self, array):
        return sort(array)


if __name__ == '__main__':
    unittest.main()

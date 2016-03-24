from test_sort_int import IntSortTest
from sorting.radix_32bit_sort import sort
import unittest

class Radix32BitSory(IntSortTest):

    # override
    def do_sort(self, array):
        return sort(array)


if __name__ == '__main__':
    unittest.main()
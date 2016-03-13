from test_sort_string import StringSortTest
from sorting.radix_sort import sort
import unittest

class RadixSortTest(StringSortTest):

    # override
    def do_sort(self, alphabet, array):
        return sort(alphabet, array)


if __name__ == '__main__':
    unittest.main()

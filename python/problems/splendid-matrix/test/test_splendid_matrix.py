import splendid_matrix.splendid_matrix as sm
import unittest


class SplendidMatrixTest(unittest.TestCase):

    def test_splendid_matrix(self):
        self.assertEqual(sm.splendid(1), [[1, 2],
                                          [3, 4]])
        self.assertEqual(sm.splendid(2), [[1, 2, 5, 6],
                                          [3, 4, 7, 8],
                                          [9, 10, 13, 14],
                                          [11, 12, 15, 16]])
        self.assertEqual(sm.splendid(3),
                         [[1, 2, 5, 6, 17, 18, 21, 22],
                          [3, 4, 7, 8, 19, 20, 23, 24],
                          [9, 10, 13, 14, 25, 26, 29, 30],
                          [11, 12, 15, 16, 27, 28, 31, 32],
                          [33, 34, 37, 38, 49, 50, 53, 54],
                          [35, 36, 39, 40, 51, 52, 55, 56],
                          [41, 42, 45, 46, 57, 58, 61, 62],
                          [43, 44, 47, 48, 59, 60, 63, 64]])


if __name__ == '__main__':
    unittest.main()

import unittest

import dec2hex.dec2hex as d2c


class Dec2HexTest(unittest.TestCase):

    def test_dec_2_hex(self):
        self.assertEqual('0x0', d2c.dec2hexa(0))
        self.assertEqual('0x1', d2c.dec2hexa(1))
        self.assertEqual('0xF', d2c.dec2hexa(15))
        self.assertEqual('0x10', d2c.dec2hexa(16))
        self.assertEqual('0x21', d2c.dec2hexa(33))

    def test_hexa_code(self):
        self.assertEqual('0', d2c.hexa_code_for(0))
        self.assertEqual('1', d2c.hexa_code_for(1))
        self.assertEqual('2', d2c.hexa_code_for(2))
        self.assertEqual('3', d2c.hexa_code_for(3))
        self.assertEqual('4', d2c.hexa_code_for(4))
        self.assertEqual('5', d2c.hexa_code_for(5))
        self.assertEqual('6', d2c.hexa_code_for(6))
        self.assertEqual('7', d2c.hexa_code_for(7))
        self.assertEqual('8', d2c.hexa_code_for(8))
        self.assertEqual('9', d2c.hexa_code_for(9))
        self.assertEqual('A', d2c.hexa_code_for(10))
        self.assertEqual('B', d2c.hexa_code_for(11))
        self.assertEqual('C', d2c.hexa_code_for(12))
        self.assertEqual('D', d2c.hexa_code_for(13))
        self.assertEqual('E', d2c.hexa_code_for(14))
        self.assertEqual('F', d2c.hexa_code_for(15))


if __name__ == '__main__':
    unittest.main()

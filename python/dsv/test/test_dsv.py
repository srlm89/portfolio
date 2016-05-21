import unittest

import dsv.dsv as dsv


class DSVTest(unittest.TestCase):

    def test_csv_asterisk(self):
        csv = dsv.DSV(',', '"', '*')
        self.check_back_and_forth(csv, '', [])
        self.check_back_and_forth(csv, 'a', ['a'])
        self.check_back_and_forth(csv, 'a,', ['a', ''])
        self.check_back_and_forth(csv, ',a,', ['', 'a', ''])
        self.check_back_and_forth(csv, 'a*b', ['a*b'])
        self.check_back_and_forth(csv, 'a*b,**', ['a*b', '**'])
        self.check_back_and_forth(csv, 'a,"b,c"', ['a', 'b,c'])
        self.check_back_and_forth(csv, 'a,"b,c",d,e', ['a', 'b,c', 'd', 'e'])
        self.check_back_and_forth(csv, 'a,"b,c*",d",e', ['a', 'b,c",d', 'e'])
        self.check_back_and_forth(csv, '"a *"b*" c",,e', ['a "b" c', '', 'e'])
        self.check_back_and_forth_2(csv, 'a*b,"**"', ['a*b', '**'], 'a*b,**')
        self.check_back_and_forth_2(csv, 'a,"b,c","d",e', ['a', 'b,c', 'd', 'e'], 'a,"b,c",d,e')

    def test_tsv_simple(self):
        tsv = self.tsv()
        self.check_back_and_forth(tsv, '', [])
        self.check_back_and_forth(tsv, 'a', ['a'])
        self.check_back_and_forth(tsv, 'a\t', ['a', ''])
        self.check_back_and_forth(tsv, '\ta\t', ['', 'a', ''])
        self.check_back_and_forth(tsv, 'a\tb', ['a', 'b'])
        self.check_back_and_forth(tsv, 'a\tb\tcd\tef gh', ['a', 'b', 'cd', 'ef gh'])

    def test_tsv_escaped(self):
        tsv = self.tsv()
        self.check_back_and_forth(tsv, 'a\tb\t"c\td"', ['a', 'b', 'c\td'])
        self.check_back_and_forth(tsv, 'a\tb\t"\t\t"', ['a', 'b', '\t\t'])
        self.check_back_and_forth(tsv, 'a\tb\t"c\td\t\te"', ['a', 'b', 'c\td\t\te'])
        self.check_back_and_forth(tsv, '"""a\t""\tb"', ['"a\t"\tb'])

    def test_csv_simple(self):
        csv = self.csv()
        self.check_back_and_forth(csv, '', [])
        self.check_back_and_forth(csv, 'a', ['a'])
        self.check_back_and_forth(csv, 'a,', ['a', ''])
        self.check_back_and_forth(csv, ',a,', ['', 'a', ''])
        self.check_back_and_forth(csv, 'whelped flunks', ['whelped flunks'])
        self.check_back_and_forth(csv, 'a,b', ['a', 'b'])
        self.check_back_and_forth(csv, 'a,,b', ['a', '', 'b'])
        self.check_back_and_forth(csv, 'a,,b,,,c', ['a', '', 'b', '', '', 'c'])
        self.check_back_and_forth(csv, 'ab, cb,ef ,gh', ['ab', ' cb', 'ef ', 'gh'])

    def test_csv_escaped(self):
        csv = self.csv()
        self.check_back_and_forth(csv, '"a "" b",', ['a " b', ''])
        self.check_back_and_forth(csv, '"a,b","c,d"', ['a,b', 'c,d'])
        self.check_back_and_forth(csv, '"a """" b",', ['a "" b', ''])
        self.check_back_and_forth(csv, '"a ""b"" c",,d', ['a "b" c', '', 'd'])
        self.check_back_and_forth(csv, '"a ""b,c"" d",,e', ['a "b,c" d', '', 'e'])
        self.check_back_and_forth(csv, '"""a,"",b"', ['"a,",b'])
        self.check_back_and_forth_2(csv, 'a "" b,', ['a "" b', ''], '"a """" b",')
        self.check_back_and_forth_2(csv, 'a "b,c" d,,e', ['a "b,c" d', '', 'e'], '"a ""b,c"" d",,e')

    def test_parse_file(self):
        csv = self.csv()
        par = csv.parse_file('./resources/SampleCSVFile_2kb.csv')
        self.assertEqual(10, len(par))
        self.assertEqual('1', par[0][0])
        self.assertEqual('', par[7][9])
        self.assertEqual('-695.26', par[8][4])
        self.assertEqual('Eldon Base for stackable storage shelf, platinum', par[0][1])
        self.assertEqual('1.7 Cubic Foot Compact "Cube" Office Refrigerators', par[1][1])
        self.assertEqual('G.E. Longer-Life Indoor Recessed Floodlight Bulbs', par[5][1])

    def check_back_and_forth(self, dsv, string, parts):
        parsed = dsv.parse(string)
        escaped = dsv.join(parts)
        self.assertEqual(parts, parsed)
        self.assertEqual(string, escaped)

    def check_back_and_forth_2(self, dsv, string, parts, written):
        escaped = dsv.join(parts)
        parsed = dsv.parse(string)
        reparsed = dsv.parse(written)
        self.assertEqual(parts, parsed)
        self.assertEqual(parts, reparsed)
        self.assertEqual(written, escaped)

    def tsv(self):
        return dsv.tsv

    def csv(self):
        return dsv.csv


if __name__ == '__main__':
    unittest.main()

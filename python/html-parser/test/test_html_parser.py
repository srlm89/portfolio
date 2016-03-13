import html_parser.html_parser as hp
import unittest


class HTMLParserTest(unittest.TestCase):

    def test_add_if_not_empty(self):
        l = []
        self.assertEqual([], hp.add_if_not_empty('', l))
        self.assertEqual(['a'], hp.add_if_not_empty('a', l))
        self.assertEqual(['a', 'b'], hp.add_if_not_empty(' b ', l))
        self.assertEqual(['a', 'b', 'ss'], hp.add_if_not_empty('\n\n\tss\n  ', l))

    def test_add_joined(self):
        l = []
        self.assertEqual([], hp.add_joined([], l))
        self.assertEqual(['a'], hp.add_joined(['', 'a', ''], l))
        self.assertEqual(['a', 'z b'], hp.add_joined(['z', 'b'], l))

    def test_can_ignore(self):
        ignoring = ['</?br/?>', 'danger']
        self.assertFalse(hp.can_ignore('<br>', []))
        self.assertTrue(hp.can_ignore('<br>', ignoring))
        self.assertTrue(hp.can_ignore('</br>', ignoring))
        self.assertTrue(hp.can_ignore('<br/>', ignoring))
        self.assertFalse(hp.can_ignore('<boron>', ignoring))
        self.assertTrue(hp.can_ignore('danger', ignoring))
        self.assertFalse(hp.can_ignore('dangerous', ignoring))

    def test_split_first(self):
        ignoring = ['23']
        keyword = '[0-9]+'
        call = lambda e: hp.split_first(e, keyword, ignoring)
        self.assertEquals([''], call(''))
        self.assertEquals(['abc'], call('abc'))
        self.assertEquals(['', '12', ''], call('12'))
        self.assertEquals(['a', '123', '.'], call('a123.'))
        self.assertEquals(['ab', '123', '..'], call('ab123..'))
        self.assertEquals(['abc', '123', '...'], call('abc123...'))
        self.assertEquals(['23'], call('23'))
        self.assertEquals(['', '232', ''], call('232'))
        self.assertEquals(['23a', '54', ''], call('23a54'))
        self.assertEquals(['23a23b', '54', 'c'], call('23a23b54c'))

    def test_parse(self):
        call = lambda e: hp.parse(iter(e))
        self.assertEqual([], call(['']))
        self.assertEqual(['a b c d'], call(['a b c d']))
        self.assertEqual(['a b c d e'], call(['a b', 'c d', 'e']))
        self.assertEqual([['<head>', 'Hi', '</head>']], call(['<head>Hi</head>']))
        self.assertEqual([['<head>', 'Hi', ['<b>', 'You!', '</b>'],'</head>']],
                         call(['<head>Hi <b>You!</b></head>']))
        self.assertEqual([['<head>', 'How', ['<i>', 'are', '</i>'], 'you?', '</head>']],
                         call(['<head>How <i>are</i> you?</head>']))
        self.assertEqual([['<h>', 'This image', ['<c>', 'was', '</c>'], 'truncated .', '</h>']],
                         call(['<h>This', 'image <c>', "was</c> truncated", '.</h>']))
        self.assertEqual([['<a>', 'The', ['<b>', 'qS', '</b>'], 'now', ['<c>', 'NULL', '</c>'], 'as.', '</a>']],
                         call(['<a>The <b>qS</b> now <c>NULL</c> as.</a>']))

    def test_parse_resource_doc_1(self):
        self.check_resource('doc-1.html',
            [['<html lang="en">',
              ['<head>', ['<title>', 'Title', '</title>'], '</head>'],
              ['<body>', '</body>'],
              '</html>']]
        )

    def test_parse_resource_table_1(self):
        self.check_resource('table-1.html',
             [['<table class="bad-table">',
              ['<thead>',
               ['<tr>',
                ['<th>', 'Service Name', '</th>'],
                ['<th>', 'Component', '</th>'],
                ['<th>', 'Replacement Service / Changes', '</th>'],
                '</tr>'],
               '</thead>'],
              '</table>']]
        )

    def test_parse_resource_table_2(self):
        self.maxDiff = None
        self.check_resource('table-2.html',
            [['<table class="bad-table">',
              ['<thead>',
               ['<tr>',
                ['<th>', 'Service Name', '</th>'],
                ['<th>', 'Component', '</th>'],
                ['<th>', 'Replacement Service / Changes', '</th>'],
                '</tr>'],
               '</thead>'],
              ['<tbody>',
               ['<td>', 'Various services', '</td>'],
               ['<td>', 'Error types', '</td>'],
               ['<td>', 'The TOO_MANY_OPERTAIONS error', '</td>'],
               ['<tr>',
                ['<td>', 'AdGroupAdService', '</td>'],
                ['<td>', 'Video template ads', '</td>'],
                ['<td>', 'Video template ads (IDs 9 and 13) are not supported.', '</td>'],
                '</tr>'],
               ['<tr>',
                ['<td>', 'AdGroupCriterionService', '</td>'],
                ['<td>', 'QualityInfo', '</td>'],
                ['<td>',
                 'The',
                 ['<code>', 'isKeywordAdRelevanceAcceptable', '</code>'],
                 'and<br>',
                 ['<code>', 'isLandingPageQualityAcceptable', '</code>'],
                 'fields have been removed.',
                 '</td>'],
                '</tr>'],
               ['<tr>',
                ['<td>', 'AdGroupCriterionService', '</td>'],
                ['<td>', 'QualityInfo.qualityScore', '</td>'],
                ['<td>',
                 'The',
                 ['<code>', 'qualityScore', '</code>'],
                 'fields can now return',
                 ['<code>', 'NULL', '</code>'],
                 'as a<br> valid value meaning data is not available.',
                 '</td>'],
                '</tr>'],
               ['<tr>',
                ['<td>', 'BudgetOrderService', '</td>'],
                ['<td>', 'BudgetOrderError', '</td>'],
                ['<td>',
                 'The BudgetOrderError.Reason enum',
                 ['<code>', 'MCC_HIERARCHY_TOO_LARGE', '</code>'],
                 'has been removed.',
                 '</td>'],
                '</tr>'],
               '</tbody>'],
              '</table>']]
        )

    def check_resource(self, path, expected):
        resource = open('./resources/%s' % path, 'r')
        result = hp.parse(iter(resource))
        resource.close()
        self.assertEqual(expected, result)


if __name__ == '__main__':
    unittest.main()

import unittest

import fixture.fixture as fixture

class FixtureTest(unittest.TestCase):

    def test_fixture_2_teams(self):
        self.check_fixture(['A', 'B'], [
            [['A', 'B']]
        ])

    def test_fixture_3_teams(self):
        self.check_fixture(['A', 'B', 'C'], [
            [['B', 'C']],
            [['A', 'B']],
            [['A', 'C']]
        ])

    def test_fixture_4_teams(self):
        self.check_fixture(['A', 'B', 'C', 'D'], [
            [['A', 'D'], ['B', 'C']],
            [['A', 'B'], ['C', 'D']],
            [['A', 'C'], ['B', 'D']]
        ])

    def test_fixture_5_teams(self):
        self.check_fixture(['A', 'B', 'C', 'D', 'E'], [
            [['B', 'E'], ['C', 'D']],
            [['A', 'B'], ['C', 'E']],
            [['A', 'C'], ['D', 'E']],
            [['A', 'D'], ['B', 'C']],
            [['A', 'E'], ['B', 'D']]
        ])

    def test_fixture_6_teams(self):
        self.check_fixture(['A', 'B', 'C', 'D', 'E', 'F'], [
            [['A', 'F'], ['B', 'E'], ['C', 'D']],
            [['A', 'B'], ['C', 'E'], ['D', 'F']],
            [['A', 'C'], ['B', 'F'], ['D', 'E']],
            [['A', 'D'], ['B', 'C'], ['E', 'F']],
            [['A', 'E'], ['B', 'D'], ['C', 'F']]
        ])

    def check_fixture(self, teams, expected):
        self.assertEqual(expected, fixture.build(teams))


if __name__ == '__main__':
    unittest.main()

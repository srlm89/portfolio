import unittest

import fixture.fixture_home_and_away as fixture

class FixtureHomeAndAwayTest(unittest.TestCase):

    def test_fixture_2_teams(self):
        self.check_fixture(['A', 'B'], [
            [['A', 'B']],
            [['B', 'A']]
        ])

    def test_fixture_3_teams(self):
        self.check_fixture(['A', 'B', 'C'], [
            [['B', 'C']],
            [['B', 'A']],
            [['A', 'C']],
            [['C', 'B']],
            [['A', 'B']],
            [['C', 'A']]
        ])

    def test_fixture_4_teams(self):
        self.check_fixture(['A', 'B', 'C', 'D'], [
            [['A', 'D'], ['B', 'C']],
            [['B', 'A'], ['D', 'C']],
            [['A', 'C'], ['B', 'D']],
            [['D', 'A'], ['C', 'B']],
            [['A', 'B'], ['C', 'D']],
            [['C', 'A'], ['D', 'B']]
        ])

    def test_fixture_5_teams(self):
        self.check_fixture(['A', 'B', 'C', 'D', 'E'], [
            [['B', 'E'], ['C', 'D']],
            [['B', 'A'], ['E', 'C']],
            [['A', 'C'], ['D', 'E']],
            [['D', 'A'], ['C', 'B']],
            [['A', 'E'], ['B', 'D']],
            [['E', 'B'], ['D', 'C']],
            [['A', 'B'], ['C', 'E']],
            [['C', 'A'], ['E', 'D']],
            [['A', 'D'], ['B', 'C']],
            [['E', 'A'], ['D', 'B']]
        ])

    def test_fixture_6_teams(self):
        self.check_fixture(['A', 'B', 'C', 'D', 'E', 'F'], [
            [['A', 'F'], ['B', 'E'], ['C', 'D']],
            [['B', 'A'], ['E', 'C'], ['F', 'D']],
            [['A', 'C'], ['B', 'F'], ['D', 'E']],
            [['D', 'A'], ['C', 'B'], ['F', 'E']],
            [['A', 'E'], ['B', 'D'], ['C', 'F']],
            [['F', 'A'], ['E', 'B'], ['D', 'C']],
            [['A', 'B'], ['C', 'E'], ['D', 'F']],
            [['C', 'A'], ['F', 'B'], ['E', 'D']],
            [['A', 'D'], ['B', 'C'], ['E', 'F']],
            [['E', 'A'], ['D', 'B'], ['F', 'C']]
        ])

    def check_fixture(self, teams, expected):
        self.assertEqual(expected, fixture.build(teams))


if __name__ == '__main__':
    unittest.main()

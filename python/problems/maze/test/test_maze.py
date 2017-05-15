import unittest

import maze.maze as maze


class MazeTest(unittest.TestCase):

    MAZE_1 = {
        'rows': 9,
        'cols': 10,
        'maze': [
            [0, 1, 0, 0, 0, 0, 1, 0, 0, 0],
            [0, 1, 0, 1, 0, 0, 0, 1, 0, 0],
            [0, 0, 0, 1, 0, 0, 1, 0, 1, 0],
            [1, 1, 1, 1, 0, 1, 1, 1, 1, 0],
            [0, 0, 0, 1, 0, 0, 0, 1, 0, 1],
            [0, 1, 0, 0, 0, 0, 1, 0, 1, 1],
            [0, 1, 1, 1, 1, 1, 1, 1, 1, 0],
            [0, 1, 0, 0, 0, 0, 1, 0, 0, 0],
            [0, 0, 1, 1, 1, 1, 0, 1, 1, 0]
        ]
    }

    def test_maze_1(self):
        self.assertEqual(10, self.steps(MazeTest.MAZE_1, [2, 4]))
        self.assertEqual(23, self.steps(MazeTest.MAZE_1, [8, 1]))
        self.assertEqual(14, self.steps(MazeTest.MAZE_1, [4, 6]))
        self.assertEqual(-1, self.steps(MazeTest.MAZE_1, [3, 3]))

    def steps(self, testmaze, coordinate):
        return maze.shortest_path(
            testmaze['maze'],
            testmaze['rows'],
            testmaze['cols'],
            coordinate[0],
            coordinate[1]
        )


if __name__ == '__main__':
    unittest.main()

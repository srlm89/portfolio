### Maze

Given a maze, which is a matrix where each coordinate `(x,y)` has two possible values:
- If `(x,y) == 1`: the path is blocked
- If `(x,y) == 0`: the path is ublocked

Find the shortest path in the maze to go to a specified coordinate `(x,y)` from the starting position at `(0,0)`.
The shortest path is the number of moves you can go through wherever the path is ublocked. 

For instance, in this maze:


```
    0 1 2 3 4 5 6 7 8 9
  +--------------------
0 | 0 1 0 0 0 0 1 0 0 0
1 | 0 1 0 1 0 0 0 1 0 0
2 | 0 0 0 1 0 0 1 0 1 0
3 | 1 1 1 1 0 1 1 1 1 0
4 | 0 0 0 1 0 0 0 1 0 1
5 | 0 1 0 0 0 0 1 0 1 1
6 | 0 1 1 1 1 1 1 1 1 0
7 | 0 1 0 0 0 0 1 0 0 0
8 | 0 0 1 1 1 1 0 1 1 0 
```

The shotest path to `(2,4)` is 10, the number of steps for this path:

```
    0 1 2 3 4 5 6 7 8 9
  +--------------------
0 | 0   0 0 0
1 | 0   0   0
2 | 0 0 0   0
3 |
4 |
5 |
6 |
7 |
8 | 
```
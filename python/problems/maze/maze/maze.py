def shortest_path(maze, rows, cols, exit_row, exit_col):
    distance = 1
    distances = new_matrix(rows, cols)
    options = options_for(maze, rows, cols, 0)
    while len(options) > 0:
        new_options = []
        for xy in options:
            r = row(rows, cols, xy)
            c = col(rows, cols, xy)
            if distances[r][c] == -1:
                distances[r][c] = distance
                new_options.extend(options_for(maze, rows, cols, xy))
        options = new_options
        distance += 1
    return distances[exit_row][exit_col]


def new_matrix(rows, cols, start_row = 0, start_col = 0):
    matrix = [ [ (-1) for j in range(cols) ] for i in range(rows) ]
    matrix[start_row][start_col] = 0
    return matrix


def options_for(maze, rows, cols, value):
    options = []
    r = row(rows, cols, value)
    c = col(rows, cols, value)
    if r > 0 and maze[r-1][c] == 0:
        options.append(cell(rows, cols, r-1, c))
    if c > 0 and maze[r][c-1] == 0:
        options.append(cell(rows, cols, r, c-1))
    if r+1 < rows and maze[r+1][c] == 0:
        options.append(cell(rows, cols, r+1, c))
    if c+1 < cols and maze[r][c+1] == 0:
        options.append(cell(rows, cols, r, c+1))
    return options


def row(rows, cols, value):
    return value / cols


def col(rows, cols, value):
    return value % cols


def cell(rows, cols, row, col):
    return cols * row + col
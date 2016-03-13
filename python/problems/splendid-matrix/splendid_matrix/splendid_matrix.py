
def splendid(n):
    exp_n = pow(2, n)
    m = [ [ 0 for i in range(exp_n) ] for i in range(exp_n) ]
    def fill(m, v, x, y, d):
        if d == 1:
            m[x - 1][y - 1] = v
            v = v + 1
        else:
            v = fill(m, v, x, y, d / 2)
            v = fill(m, v, x, y + d / 2, d / 2)
            v = fill(m, v, x + d / 2, y, d / 2)
            v = fill(m, v, x + d / 2, y + d / 2, d / 2)
        return v
    fill(m, 1, 1, 1, exp_n)
    return m

def sort(alphabet, array):
    copy = list(array)
    radixsort(alphabet, copy)
    return copy

def radixsort(alphabet, l):
    radixsortrec([''] + alphabet, l, 0, len(l), 1)

def radixsortrec(alphabet, l, start, end, d):
    done = start
    for c in alphabet:
        pos = 0
        for i in range(done, end):
            if digit(l[i], d) == c:
                swap(l, done + pos, i)
                pos += 1
        if len(c) > 0 and pos > 0:
            radixsortrec(alphabet, l, done, done + pos, d + 1)
        done += pos

def digit(string, d):
    if len(string) < d:
        return ''
    return string[d-1]

def swap(array, i, j):
    tmp = array[i]
    array[i] = array[j]
    array[j] = tmp
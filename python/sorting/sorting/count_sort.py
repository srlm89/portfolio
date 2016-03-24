def sort(array):
    copy = list(array)
    countingsort(copy)
    return copy

def countingsort(array):
    if len(array) > 0:
        min, max = minmax(array)
        bucks = buckets(array, min, max)
        write_from_buckets(array, min, max, bucks)

def minmax(array):
    min = None
    max = None
    for e in array:
        if min is None or e < min:
            min = e
        if max is None or e > max:
            max = e
    return (min, max)

def buckets(array, min, max):
    total = max - min + 1
    buckets = [0 for i in range(min, max + 1)]
    for e in array:
        buckets[e - min] += 1
    return buckets

def write_from_buckets(array, min, max, buckets):
    p = 0
    for i in range(max - min + 1):
        count = buckets[i]
        for r in range(count):
            array[p] = min + i
            p += 1
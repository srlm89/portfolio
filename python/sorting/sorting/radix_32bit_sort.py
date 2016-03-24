def sort(array):
    copy = list(array)
    radix_32bit_sort(copy)
    return copy

def radix_32bit_sort(array):
    n = len(array)
    buffer = list(array)
    for shift in range(32):
        mask = 1 << shift
        ones = 0
        zero = 0
        for e in array:
            if not e & mask:
                ones += 1
        if shift == 31:
            zero = n - ones
            ones = 0
        for e in array:
            if e & mask:
                buffer[ones] = e
                ones += 1
            else:
                buffer[zero] = e
                zero += 1
        buffer, array = array, buffer
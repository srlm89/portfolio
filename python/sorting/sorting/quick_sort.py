def sort(array):
    copy = list(array)
    quicksort(copy)
    return copy

def quicksort(array):
    quicksortrec(array, 0, len(array) - 1)

def quicksortrec(array, start, end):
    if start < end:
        pivot = end
        pivot_order = partition(array, start, end, pivot)
        quicksortrec(array, start, pivot_order - 1)
        quicksortrec(array, pivot_order + 1, end)

def partition(array, start, end, pivot):
    swap(array, start, pivot)
    while start < end:
        if array[start] > array[start + 1]:
            swap(array, start + 1, start)
            start += 1
        else:
            swap(array, start + 1, end)
            end -= 1
    return start


def swap(array, i, j):
    tmp = array[i]
    array[i] = array[j]
    array[j] = tmp
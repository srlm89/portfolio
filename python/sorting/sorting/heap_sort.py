def sort(array):
    copy = list(array)
    heapsort(copy)
    return copy

def heapsort(array):
    n = len(array)
    for i in range(1, n):
        push(array, i, array[i])
    for i in range(n - 1):
        pop_max(array, n - i)

def push(maxheap, heapsize, i_value):
    i = heapsize
    parent = (i -1) / 2
    maxheap[i] = i_value
    while parent >= 0 and i_value > maxheap[parent]:
        swap(maxheap, parent, i)
        i = parent
        parent = (i - 1) / 2

def pop_max(maxheap, heapsize):
    last = heapsize - 1
    unord = last
    ascend = 0
    while maxheap[ascend] > maxheap[unord]:
        swap(maxheap, unord, ascend)
        left = 2 * ascend + 1
        right = 2 * ascend + 2
        if left < last:
            unord = ascend
            if right < last and maxheap[left] < maxheap[right]:
                ascend = right
            else:
                ascend = left

def swap(array, i, j):
    tmp = array[i]
    array[i] = array[j]
    array[j] = tmp
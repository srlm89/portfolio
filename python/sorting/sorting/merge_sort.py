def sort(array):
    copy = list(array)
    mergesort(copy)
    return copy

def mergesort(array):
	n = len(array)
	copy = list(array)
	mergesortrec(array, copy, 0, n - 1)

def mergesortrec(array, copy, start, end):
	if start < end:
		mid = (start + end) / 2
		mergesortrec(array, copy, start, mid)
		mergesortrec(array, copy, mid + 1, end)
		mergehalves(array, copy, start, mid, end)

def mergehalves(array, copy, start, mid, end):
	x = start
	i = start
	j = mid + 1
	while x <= end:
		if (i > mid) or (j <= end and copy[j] < copy[i]):
			array[x] = copy[j]
			j = j + 1
		else:
			array[x] = copy[i]
			i = i + 1
		x = x + 1
	for b in range(x):
		copy[b] = array[b]
#!/usr/bin/python -B

from random import randint
from timeit import default_timer as timer

from count_sort import sort as countsort
from heap_sort import sort as heapsort
from merge_sort import sort as mergesort
from quick_sort import sort as quicksort
from radix_32bit_sort import sort as radix32sort

ll = []
n = 1000
for i in range(n):
    k = randint(10, 5000)
    l = []
    for j in range(k):
        e = randint(-999, 999)
        l.append(e)
    ll.append(l)

functions = [
    ('python', sorted),
    ('radix sort', radix32sort),
    ('quick sort', quicksort),
    ('heap sort', heapsort),
    ('count sort', countsort),
    ('merge sort', mergesort)
]

for (name, function) in functions:
    start = timer()
    for l in ll:
        function(l)
    end = timer()
    print '%s: %s seconds' % (name, end - start)

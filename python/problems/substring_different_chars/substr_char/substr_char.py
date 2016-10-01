def longest_diff_chars(text, k):
    s = 0
    dif = 0
    n = len(text)
    beg = 0
    end = 0
    reps = {c: 0 for c in text}
    for i in range(n):
        reps[text[i]] += 1
        if reps[text[i]] == 1:
            dif += 1
        if dif == k:
            if (end - beg) < (i - s):
                beg, end = s, i
        while dif > k and s < i and reps[text[s]] > 0:
            if reps[text[s]] == 1:
                dif -= 1
            reps[text[s]] -= 1
            s += 1
    return text[beg:end + 1]


def shortest_diff_chars(text, k):
    s = 0
    dif = 0
    n = len(text)
    beg = 0
    end = n
    reps = {c: 0 for c in text}
    for i in range(n):
        reps[text[i]] += 1
        if reps[text[i]] == 1:
            dif += 1
        while s < i and reps[text[s]] > 1:
            reps[text[s]] -= 1
            s += 1
        if dif == k:
            if (end - beg) > (i - s):
                beg, end = s, i
            reps[text[s]] -= 1
            dif -= 1
            s += 1
    return text[beg:end + 1]

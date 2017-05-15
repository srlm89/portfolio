def has_balanced_brackets(string):
    stack = []
    for c in string:
        if is_opener(c):
            stack.append(c)
        elif is_closer(c):
            if not matches_open(c, stack):
                return False
    return len(stack) == 0


brackets = {
    ')': '(',
    '}': '{',
    ']': '[',
    '>': '<'
}


def matches_open(closer, stack):
    if len(stack) > 0:
        opener = stack.pop()
        return opener == brackets[closer]
    return False


def is_opener(c):
    return c in brackets.values()


def is_closer(c):
    return c in brackets.keys()

def atom(token):
    try: return int(token)
    except ValueError:
        try: return float(token)
        except ValueError:
            return str(token)


def tokenize(line):
    pairs = 0
    opens = []
    line = line.strip()
    tokens = line.replace('(', ' ( ').replace(')', ' ) ').split()
    for i in range(len(tokens)):
        token = tokens[i]
        if token == '(':
            opens.append('$')
            pairs += 1
        elif token == ')':
            if len(opens) == 0:
                raise SyntaxError("Unbalanced closing parentheses ')'")
            opens.pop()
        if token == ')' and tokens[i-1] == '.' or token == '.' and (i == 0 or tokens[i-1] == '('):
            raise SyntaxError("Illegal use of dot notation")
        else:
            tokens[i] = atom(token)
    if len(opens) > 0:
        raise SyntaxError('There are %d unclosed parentheses' % len(opens))
    if (pairs == 0 and len(tokens) != 1) or pairs > 0 and (line[0] != '(' or line[-1] != ')'):
        raise SyntaxError('Not a LISP expression (missing surrounding parentheses?)')
    return tokens


def mexpr(line):
    tokens = tokenize(line)
    stack = [None]
    for token in reversed(tokens):
        if token == ')':
            push = []
        elif token == '(':
            car = stack.pop(0)
            cdr = stack.pop(0)
            push = [car, cdr]
        elif token == '.':
            push = stack.pop(0)[0]
        else:
            cdr = stack.pop(0)
            push = [token, cdr]
        stack.insert(0, push)
    return stack.pop()[0]


def sexpr(mexpr):
    is_list = lambda e: isinstance(e, (tuple, list))
    stack = [[ mexpr, [] ]]
    pieces = []
    while len(stack) > 0:
        e = stack.pop()
        if is_list(e):
            if is_list(e[0]) and is_list(e[1]):
                push = ['(', e[0], ')', e[1]]
            if is_list(e[0]) and not is_list(e[1]):
                push = ['(', '(', e[0], ')', '.', e[1], ')']
            if not is_list(e[0]) and is_list(e[1]):
                push = [e[0], e[1]]
            if not is_list(e[0]) and not is_list(e[1]):
                push = [e[0], '.', e[1]]
            for c in push:
                if c != []:
                    stack.append(c)
        else:
            pieces.insert(0, str(e))
    return ' '.join(pieces)
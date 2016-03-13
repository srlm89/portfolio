from parser import sexpr
import support as aux

def car(x):
    try:
        return x[0]
    except IndexError as e:
        raise RuntimeError(e)

def cdr(x):
    try:
        return x[1]
    except IndexError as e:
        raise RuntimeError(e)

def cons(e, l):
    return [e, l]

def cadar(x):
    return car(cdr(car(x)))

def caddr(x):
    return car(cdr(cdr(x)))

def cdar(x):
    return cdr(car(x))

def cadr(x):
    return car(cdr(x))

def caar(x):
    return car(car(x))

def is_symbol(x):
    return isinstance(x, str)

def is_atom(x):
    return not isinstance(x, list)

def is_null(x):
    return x == []

def pairlis(x, y, a):
    if is_null(x):
        return a
    head = cons(car(x), car(y))
    tail = pairlis(cdr(x), cdr(y), a)
    return cons(head, tail)

def assoc(x, a):
    if is_null(a):
        raise RuntimeError('Undefined identifier: %s' % x)
    if caar(a) == x:
        return car(a)
    return assoc(x, cdr(a))

def evcon(c, a):
    if is_null(c):
        raise RuntimeError('Exhausted every possible condition')
    if eval(caar(c), a):
        return eval(cadar(c), a)
    return evcon(cdr(c), a)

def evlis(m, a):
    if is_null(m):
        return []
    head = eval(car(m), a)
    tail = evlis(cdr(m), a)
    return cons(head, tail)

def eval(e, a):
    if is_atom(e):
        if is_symbol(e):
            return cdr(assoc(e, a))
        return e
    if is_atom(car(e)):
        if car(e) == 'quote':
            return cadr(e)
        if car(e) == 'cond':
            return evcon(cdr(e), a)
        if car(e) == 'lambda':
            return e
        if car(e) == 'define':
            with_new_definition = cons(cons(cadr(e), cadr(e)), a)
            return apply(car(e), evlis(cdr(e), with_new_definition), a)
    return apply(car(e), evlis(cdr(e), a), a)

def apply(fn, x, a):
    if is_atom(fn):
        if fn == 'car':
            return caar(x)
        if fn == 'cdr':
            return cdar(x)
        if fn == 'cons':
            return cons(car(x), cadr(x))
        if fn == 'eq?':
            return car(x) == cadr(x)
        if fn == 'atom?':
            return is_atom(car(x))
        if fn == 'symbol?':
            return is_symbol(car(x))
        if fn == 'null?':
            return is_null(car(x))
        if fn == '+':
            return car(x) + cadr(x)
        if fn == 'exit':
            raise EOFError
        if fn == 'error':
            raise RuntimeError(sexpr(x))
        if fn == 'import':
            return aux.load(car(x), a)
        if fn == 'context':
            return aux.execution_context
        if fn == 'reset':
            return aux.clear_list()
        if fn == 'define':
            return aux.add_definition(car(x), cadr(x))
        if is_symbol(fn):
            return apply(eval(fn, a), x, a)
        return fn
    if car(fn) == 'lambda':
        extended = pairlis(cadr(fn), x, a)
        return eval(caddr(fn), extended)
    if car(fn) == 'label':
        app = cons(cons(cadr(fn), caddr(fn)), a)
        return apply(caddr(fn), x, app)
    raise RuntimeError('Unable to apply: %s' % sexpr(fn))
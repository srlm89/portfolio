from parser import mexpr
from parser import sexpr
import re

def to_lisp_list(l):
    lisp_list = []
    for e in reversed(l):
        lisp_list = [e, lisp_list]
    return lisp_list

def to_plain_list(l):
    plain = []
    while l != []:
        plain.append(l[0])
        l = l[1]
    return plain

base_context = to_lisp_list([
    ['#t', True],
    ['#f', False]
])

execution_context = list(base_context)

def clear_list(lisp_list = execution_context):
    while len(lisp_list) != 0:
        lisp_list.pop()
    for e in base_context:
        lisp_list.append(e)
    return lisp_list

def add_definition(x, y, lisp_list = execution_context):
    cdr = lisp_list.pop()
    car = lisp_list.pop()
    lisp_list.append([x, y])
    lisp_list.append([car, cdr])
    return x

def load(filename, context):
    with open(filename) as file:
        current = ''
        for line in file:
            if re.search('^;', line):
                continue
            elif re.search('\S', line):
                current += ' ' + line.rstrip('\n')
            elif len(current) > 0:
                execute(current, context)
                current = ''
        if (len(current) > 0):
            execute(current, context)
    return (filename + " successfully loaded!")

def exists_eval_in_lisp(context):
    names = map(lambda e: e[0], to_plain_list(context))
    return '*eval' in names

def execute(line, context = execution_context):
    import core
    parsed = mexpr(line)
    if exists_eval_in_lisp(context):
        result = core.apply('*eval', to_lisp_list([parsed, context]), context)
    else:
        result = core.eval(parsed, context)
    string = sexpr(result)
    return (string, result)

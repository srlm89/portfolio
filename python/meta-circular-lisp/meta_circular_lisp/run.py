#!/usr/bin/python -B

from support import execute
import traceback
import readline
import sys

def main(prompt = 'lisp> '):
    sys.setrecursionlimit(10000)
    while True:
        try:
            line = raw_input(prompt)
            (sexpr, mexpr) = execute(line)
            print sexpr
        except (KeyboardInterrupt, EOFError):
            break
        except Exception as e:
            if '-D' in sys.argv:
                traceback.print_exc()
            else:
                print str(e)
    print '\n'

if __name__ == '__main__':
    main()

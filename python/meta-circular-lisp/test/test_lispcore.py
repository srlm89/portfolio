#!/usr/bin/python -B

import meta_circular_lisp.support as aux
import test_core
import unittest

class LispCoreTest(test_core.CoreTest):

    @classmethod
    def setUpClass(cls):
        core = '../lisp/core.lisp'
        aux.load(core, cls.ctx)
        aux.load(core, aux.execution_context)

    def evals(self, input, expected, context = aux.execution_context):
        (_, actual) = aux.execute(input, context)
        self.assertEquals(expected, actual)

if __name__ == '__main__':
    unittest.main()

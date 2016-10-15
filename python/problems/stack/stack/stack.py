
class EmptyStackNode:

    def __init__(self):
        pass

    def pop(self):
        raise RuntimeError("No such element: Empty Stack")

    def push(self, x):
        return StackNode(self, x)


class StackNode:

    def __init__(self, inner, x):
        self.inner = inner
        self.x = x

    def push(self, x):
        return StackNode(self, x)

    def pop(self):
        return [self.inner, self.x]


class Stack:

    def __init__(self):
        self.stack = EmptyStackNode()

    def push(self, x):
        self.stack = self.stack.push(x)

    def pop(self):
        [self.stack, val] = self.stack.pop()
        return val

class Node:

    def __init__(self, value):
        self._height = 1
        self._value = value
        self._left = None
        self._right = None
        self._parent = None

    def height(self):
        return self._height

    def value(self):
        return self._value

    def left(self):
        return self._left

    def right(self):
        return self._right

    def parent(self):
        return self._parent

    def height_l(self):
        if self._left:
            return self._left.height()
        return 0

    def height_r(self):
        if self._right:
            return self._right.height()
        return 0

    def height_factor(self):
        return self.height_l() - self.height_r()

    def change_value(self, value):
        self._value = value

    def link_replace(self, child, new_child):
        if child is self._left:
            self._left._parent = None
            return self.link_l(new_child)
        if child is self._right:
            self._right._parent = None
            return self.link_r(new_child)

    def link_l(self, node):
        self._left = node
        return self.update_with(node)

    def link_r(self, node):
        self._right = node
        return self.update_with(node)

    def update_with(self, child):
        if child:
            child._parent = self
        self.update_height()
        return child

    def update_height(self):
        old = self._height
        new = max(self.height_l(), self.height_r()) + 1
        if old !=  new:
            self._height = new
            if self._parent:
                self._parent.update_height()

    def folded(self):
        folded = []
        stack = [[self, folded]]
        while len(stack) > 0:
            node, current = stack.pop()
            if node:
                current.append(node.value())
                for child in [node.left(), node.right()]:
                    subtree = []
                    current.append(subtree)
                    stack.append([child, subtree])
        return folded

    def flat(self):
        values = []
        stack = [self]
        while len(stack) > 0:
            node = stack.pop(0)
            if node:
                values.append(node.value())
                for child in [node.right(), node.left()]:
                    stack.insert(0, child)
        return values

    def __str__(self):
        return '%s %s' % (self._value, [self._height])

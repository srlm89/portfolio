from node import Node

class AVLTree:
    
    def __init__(self):
        self._root = None
        self._size = 0

    def root(self):
        return self._root

    def size(self):
        return self._size

    def folded(self):
        l = []
        if self._root:
            l = self._root.folded()
        return l

    def flat(self):
        l = []
        if self._root:
            l = self._root.flat()
        return l

    def min(self):
        if self._root:
            return self.min_from(self._root)

    def min_from(self, node):
        while node.left():
            node = node.left()
        return node

    def max(self):
        if self._root:
            return self.max_from(self._root)

    def max_from(self, node):
        while node.right():
            node = node.right()
        return node

    def contains(self, value):
        return self.find(value) is not None

    def find(self, value):
        node = self._root
        while node and node.value() != value:
            if value < node.value():
                node = node.left()
            else:
                node = node.right()
        return node

    def insert_all(self, values):
        for value in values:
            self.insert(value)

    def insert(self, value):
        node = self.binary_insertion(value)
        self.balance_tree(node)

    def binary_insertion(self, value):
        node = self._root
        call = self.init_root
        while node:
            if node.value() == value:
                return None
            if value < node.value():
                call = node.link_l
                node = node.left()
            else:
                call = node.link_r
                node = node.right()
        self._size += 1
        return call(Node(value))

    def remove(self, value):
        victim = self.find(value)
        if victim:
            relocated = self.relocate_children(victim)
            self.balance_tree(relocated)
            self._size -= 1

    def relocate_children(self, evicted):
        if evicted.left() and evicted.right():
            evicted = self.successor_of(node)
            node.change_value(evicted.value())
            surrogate = evicted.right()
            relocated = evicted.parent()
        elif evicted.left():
            surrogate = evicted.left()
            relocated = surrogate
        elif evicted.right():
            surrogate = evicted.right()
            relocated = surrogate
        else:
            surrogate = None
            relocated = evicted.parent()
        self.replace_parent(evicted, surrogate)
        return relocated

    def successor_of(self, node):
        if node:
            if node.right():
                node = self.min_from(node.right())
            else:
                value = node.value()
                while node and node.value() <= value:
                    node = node.parent()
        return node

    def balance_tree(self, node):
        while node:
            factor = node.height_factor()
            if -2 < factor < 2:
                node = node.parent()
                continue
            if factor == 2:
                if node.left().height_factor() == 1:
                    self.balance_left_left(node)
                else:
                    self.balance_left_right(node)
            else:
                if node.right().height_factor() == 1:
                    self.balance_right_left(node)
                else:
                    self.balance_right_right(node)
    
    def balance_left_left(self, node):
        return self.rotate_right(node, node.left())
    
    def balance_right_right(self, node):
        return self.rotate_left(node, node.right())
    
    def balance_left_right(self, node):
        self.rotate_left(node.left(), node.left().right())
        return self.rotate_right(node, node.left())
    
    def balance_right_left(self, node):
        self.rotate_right(node.right(), node.right().left())
        return self.rotate_left(node, node.right())

    def rotate_right(self, down, up):
        self.replace_parent(down, up)
        down.link_l(up.right())
        up.link_r(down)
        return up

    def rotate_left(self, down, up):
        self.replace_parent(down, up)
        down.link_r(up.left())
        up.link_l(down)
        return up

    def replace_parent(self, evicted, surrogate):
        if evicted is self._root:
            if surrogate:
                surrogate.parent().link_replace(surrogate, None)
            self._root = surrogate
        elif evicted:
            evicted.parent().link_replace(evicted, surrogate)
        return surrogate

    def init_root(self, node):
        self._root = node

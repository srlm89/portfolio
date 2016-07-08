import math, random


def identityMetaclass():
	return createMetaclass(lambda cls: Function.identities.append(cls))


def compositeMetaclass():
	return createMetaclass(lambda cls: Function.composites.append(cls))


def ignoreMetaclass():
	return createMetaclass(lambda cls: cls is None)


def maybeCompositeMetaclass():
	f = ignoreMetaclass
	if random.choice([True, False]):
		f = compositeMetaclass
	return f()


def createMetaclass(f):
	class Meta(type):
		def __init__(cls, name, bases, dct):
			super(Meta, cls).__init__(name, bases, dct)
			f(cls)
	return Meta


class Function:
	"""Functions must have domain and codomain [-1,1] x [-1,1]"""

	composites = []
	identities = []

	def __init__(self, prob=0.99):
		self.f = self.newfun(prob)

	def newfun(self, prob):
		if random.random() < prob:
			return random.choice(self.composites)(prob ** 2)
		else:
			return random.choice(self.identities)()

	def __str__(self):
		return str(self.f)

	def value(self, x, y):
		return self.f.value(x, y)


class ByPi:

	def __init__(self):
		self.m = random.choice([0.25, 0.5, 0.75, 1.0, 1.25, 1.5, 1.75])
		self.v = math.pi * self.m

	def __str__(self):
		return '%s*pi' % str(self.m)

	def factor(self):
		return self.v

	def value(self, x, y):
		return self.v


class IdX:

	__metaclass__ = identityMetaclass()

	def __str__(self):
		return 'x'

	def value(self, x, y):
		return x


class IdY:

	__metaclass__ = identityMetaclass()

	def __str__(self):
		return 'y'

	def value(self, x, y):
		return y


class Sine:

	__metaclass__ = compositeMetaclass()

	def __init__(self, prob):
		self.f = Function(prob)

	def __str__(self):
		return 'sin(%s)' % (str(self.f))

	def value(self, x, y):
		return math.sin(self.f.value(x, y))


class Cos:

	__metaclass__ = compositeMetaclass()

	def __init__(self, prob):
		self.f = Function(prob)

	def __str__(self):
		return 'cos(%s)' % (str(self.f))

	def value(self, x, y):
		return math.cos(self.f.value(x, y))


class AdjustedSine:

	__metaclass__ = compositeMetaclass()

	def __init__(self, prob):
		self.f = Function(prob)
		self.m = ByPi()

	def __str__(self):
		return 'sin(%s * %s)' % (str(self.m), str(self.f))

	def value(self, x, y):
		return math.sin(self.m.factor() * self.f.value(x, y))


class AdjustedCos:

	__metaclass__ = compositeMetaclass()

	def __init__(self, prob):
		self.f = Function(prob)
		self.m = ByPi()

	def __str__(self):
		return 'cos(%s * %s)' % (str(self.m), str(self.f))

	def value(self, x, y):
		return math.cos(self.m.factor() * self.f.value(x, y))


class SumSine:

	__metaclass__ = compositeMetaclass()

	def __init__(self, prob):
		self.f1 = Function(prob)
		self.f2 = Function(prob)

	def __str__(self):
		return 'sin(%s + %s)' % (str(self.f1), str(self.f2))

	def value(self, x, y):
		return math.sin(self.f1.value(x,y) + self.f2.value(x,y))


class SumCos:

	__metaclass__ = compositeMetaclass()

	def __init__(self, prob):
		self.f1 = Function(prob)
		self.f2 = Function(prob)

	def __str__(self):
		return 'cos(%s + %s)' % (str(self.f1), str(self.f2))

	def value(self, x, y):
		return math.cos(self.f1.value(x,y) + self.f2.value(x,y))


class Multiplied:

	__metaclass__ = compositeMetaclass()

	def __init__(self, prob):
		self.f1 = Function(prob)
		self.f2 = Function(prob)

	def __str__(self):
		return '%s * %s' % (str(self.f1), str(self.f2))

	def value(self, x, y):
		return self.f1.value(x,y) * self.f2.value(x,y)


class Average:

	__metaclass__ = maybeCompositeMetaclass()

	def __init__(self, prob):
		self.f1 = Function(prob)
		self.f2 = Function(prob)

	def __str__(self):
		return 'avg(%s, %s)' % (str(self.f1), str(self.f2))

	def value(self, x, y):
		return 0.5*(self.f1.value(x,y) + self.f2.value(x,y))


class AbsoluteValue:

	__metaclass__ =	maybeCompositeMetaclass()

	def __init__(self, prob):
		self.f = Function(prob)

	def __str__(self):
		return 'abs(%s)' % str(self.f)

	def value(self, x, y):
		return abs(self.f.value(x,y))


class Invert:

	__metaclass__ = maybeCompositeMetaclass()

	def __init__(self, prob):
		self.f = Function(prob)

	def __str__(self):
		return '-%s' % str(self.f)

	def value(self, x, y):
		return -self.f.value(x,y)


class Swap:

	__metaclass__ = maybeCompositeMetaclass()

	def __init__(self, prob):
		self.f = Function(prob)

	def __str__(self):
		return 'swap(%s)' % str(self.f)

	def value(self, x, y):
		return self.f.value(y,x)


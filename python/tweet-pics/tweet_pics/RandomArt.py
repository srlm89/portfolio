import Log
from PIL import Image
from Math import Function

def createFunction(name):
	function = Function()
	Log.logger.info('%s: %s' % (name, str(function)))
	return function

def randomChannel(dimension, name):
	(width, height) = dimension
	function = createFunction(name)
	halfX = (width - 1) * 0.5
	halfY = (height - 1) * 0.5
	channel = Image.new('L', (width, height))
	for r in range(height):
		y = float(halfY - r) / halfY
		for c in range(width):
			x = float(c - halfX) / halfX
			z = function.value(x, y)
			pixel = int(z * 127.5 + 127.5)
			channel.putpixel((c, r), pixel)
	return channel

def randomPicture(dimension):
	redChannel = randomChannel(dimension, 'red')
	greenChannel = randomChannel(dimension, 'green')
	blueChannel = randomChannel(dimension, 'blue')
	return Image.merge('RGB', (redChannel, greenChannel, blueChannel))

def draw(outputPath, width = 420, scale = 16.0/9):
	height = int(width / float(scale))
	image = randomPicture((width, height))
	image.save(outputPath)


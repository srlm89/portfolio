
import re

def readProperties(filePath):
	properties = {}
	regex = re.compile('^([A-Z_]+) *= *(.+)$')
	for line in readLines(filePath):
		match = re.match(regex, line)
		if match:
			properties[match.group(1)] = match.group(2)
	return properties

def readLines(filePath):
	return openCallClose(filePath, lambda e: e.readlines())

def readData(filePath):
	return openCallClose(filePath, lambda e: e.read())

def openCallClose(filePath, function):
	openFile = open(filePath, 'r')
	result = function(openFile)
	openFile.close()
	return result


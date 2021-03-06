import logging

logger = None

def shutdown():
	logging.shutdown()

def configure(logPath):
	global logger
	logging.basicConfig(
		format='%(asctime)s %(levelname)s | %(message)s',
		datefmt='%Y/%m/%d %I:%M:%S %p',
		level=logging.INFO,
		filename=logPath,
		filemode='a'
	)
	logger = logging.getLogger()


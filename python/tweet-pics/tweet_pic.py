#!/usr/bin/python -B

from tweet_pics.Twitter import Twitter
from tweet_pics import RandomArt
from tweet_pics import Log
import traceback
import random
import os

def main(prompt = 'lisp> '):
    Log.configure('./tweet_pic.log')
    logger = Log.logger
    credentials = './token/credentials.txt'
    output = 'pic.png'
    try:
        logger.info('Instantiating API')
        api = Twitter(credentials)
        trends = api.worldwideTrends()
        random.shuffle(trends)
        text = '%s, %s, %s' % tuple(trends[:3])
        logger.info('Creating picture with seed ' + text)
        random.seed(text)
        RandomArt.draw(output)
        logger.info('Uploading picture')
        api.tweetWithImage('\n'.join(trends[:3]), output)
        logger.info('Deleting picture')
        os.remove(output)
    except Exception as e:
        logger.warn(traceback.format_exc())
    finally:
        Log.shutdown()

if __name__ == '__main__':
    main()
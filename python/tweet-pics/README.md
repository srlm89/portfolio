### Install PIP

    sudo apt-get install python-pip python-dev build-essential
    sudo pip install --upgrade pip
    sudo pip install --upgrade virtualenv


### Install PIL

	sudo apt-get install python-dev libjpeg-dev libfreetype6-dev zlib1g-dev
	sudo ln -s /usr/lib/i386-linux-gnu/libfreetype.so /usr/lib/
	sudo ln -s /usr/lib/i386-linux-gnu/libjpeg.so /usr/lib/
	sudo ln -s /usr/lib/i386-linux-gnu/libz.so /usr/lib/
	sudo pip install pillow


### Install [TwitterAPI](https://github.com/geduldig/TwitterAPI)

	pip install TwitterAPI
	pip install --upgrade requests


### Run

1. Set up your credentials property file defining your API_KEY, API_SECRET, ACCESS_TOKEN_KEY
and ACCESS_TOKEN_SECRET. You can see an example in `./token/credentials-sample.txt`.

2. Run from the console:

        python tweet_pic.py


### Inspiration

	http://jeremykun.com/2012/01/01/random-psychedelic-art/

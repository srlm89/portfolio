import IOReader
from TwitterAPI import TwitterAPI

class Twitter:

	def __init__(self, credentials):
		self.twitterApi = self.createApi(credentials)

	def tweet(self, message):
		tweet = { 'status' : message }
		return self.requestIsOk('statuses/update', tweet)

	def tweetWithImage(self, message, imagePath):
		tweet = { 'status' : message }
		pic = { 'media[]' : IOReader.readData(imagePath) }
		return self.requestIsOk('statuses/update_with_media', tweet, pic)

	def worldwideTrends(self):
		return self.trendsFor(1)

	def trendsIn(self, country):
		trends = set()
		for woeid in self.woeidsFor(country):
			trends = trends.union(self.trendsFor(woeid))
		return trends

	def woeidsFor(self, country):
		available = self.availableTrends()
		ofCountry = filter(lambda e: e['country'] == country, available)
		return map(lambda e: e['woeid'], ofCountry)

	def availableTrends(self):
		mapF = lambda e: ({ 'country' : e['country'], 'woeid' : e['woeid'] })
		response = self.request('trends/available')
		return self.maped(response, mapF)

	def trendsFor(self, woeid):
		trends = []
		response = self.request('trends/place', { 'id' : woeid })
		if response:
			trends = self.maped(response, lambda e: e['name'])
		return trends

	def requestIsOk(self, url, *args):
		return self.request(url, *args) is not None

	def request(self, url, *args):
		response = self.twitterApi.request(url, *args)
		if response.status_code == 200:
			return response

	def filtered(self, response, function):
		return filter(funcion, response.get_iterator())

	def maped(self, response, function):
		return map(function, response.get_iterator())

	def createApi(self, credentialsPath):
		credentials = IOReader.readProperties(credentialsPath)
		apiKey = credentials['API_KEY']
		apiSecret = credentials['API_SECRET']
		tokenKey = credentials['ACCESS_TOKEN_KEY']
		tokenSecret = credentials['ACCESS_TOKEN_SECRET']
		api = TwitterAPI(apiKey, apiSecret, tokenKey, tokenSecret, auth_type = 'oAuth1', proxy_url = None)
		return api

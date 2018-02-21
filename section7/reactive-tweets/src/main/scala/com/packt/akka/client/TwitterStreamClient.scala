package com.packt.akka.client

import akka.actor.ActorSystem
import com.packt.akka.model.Tweet
import com.packt.akka.model.Tweet.Author
import twitter4j._
import twitter4j.auth.AccessToken
import twitter4j.conf.ConfigurationBuilder

class TwitterStreamClient(val actorSystem: ActorSystem) {

  val factory = new TwitterStreamFactory(new ConfigurationBuilder().build())
  val twitterStream = factory.getInstance()

  def init() = {
    twitterStream.setOAuthConsumer(TwitterConfiguration.apiKey, TwitterConfiguration.apiSecret)
    twitterStream.setOAuthAccessToken(new AccessToken(TwitterConfiguration.accessToken, TwitterConfiguration.accessTokenSecret))
    twitterStream.addListener(statusListener)
    twitterStream.sample()
  }

  def statusListener = new StatusListener() {
    def onStatus(s: Status) =
      actorSystem.eventStream.publish(Tweet(Author(s.getUser.getScreenName), s.getText))

    def onDeletionNotice(statusDeletionNotice: StatusDeletionNotice) = Unit

    def onTrackLimitationNotice(numberOfLimitedStatuses: Int) = Unit

    def onException(ex: Exception) = ex.printStackTrace()

    def onScrubGeo(arg0: Long, arg1: Long) = Unit

    def onStallWarning(warning: StallWarning) = Unit
  }

  def stop() = {
    twitterStream.cleanUp()
    twitterStream.shutdown()
  }
}
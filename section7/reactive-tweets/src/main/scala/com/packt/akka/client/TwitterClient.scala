package com.packt.akka.client

import twitter4j._
import twitter4j.conf.ConfigurationBuilder

import scala.collection.JavaConverters._

object TwitterClient {

  def getInstance: Twitter = {

    val cb = new ConfigurationBuilder()
    cb.setDebugEnabled(true)
      .setOAuthConsumerKey(TwitterConfiguration.apiKey)
      .setOAuthConsumerSecret(TwitterConfiguration.apiSecret)
      .setOAuthAccessToken(TwitterConfiguration.accessToken)
      .setOAuthAccessTokenSecret(TwitterConfiguration.accessTokenSecret)

    val tf = new TwitterFactory(cb.build())
    tf.getInstance()
  }

  def retrieveTweets(term: String) = {
    val query = new Query(term)
    query.setCount(100)
    getInstance.search(query).getTweets.asScala.iterator
  }
}
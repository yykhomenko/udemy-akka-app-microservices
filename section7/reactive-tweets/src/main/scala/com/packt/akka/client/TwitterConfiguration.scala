package com.packt.akka.client

import com.typesafe.config.ConfigFactory

object TwitterConfiguration {
  val config = ConfigFactory.load.getConfig("Twitter")
  val apiKey = config.getString("apiKey")
  val apiSecret = config.getString("apiSecret")
  val accessToken = config.getString("accessToken")
  val accessTokenSecret = config.getString("accessTokenSecret")
}
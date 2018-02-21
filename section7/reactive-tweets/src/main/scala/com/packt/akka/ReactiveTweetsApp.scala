package com.packt.akka

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Sink, Source}
import com.packt.akka.client.TwitterClient
import com.packt.akka.model.Tweet
import com.packt.akka.model.Tweet.Author
import twitter4j.Status

object ReactiveTweetsApp extends App {

  implicit val system = ActorSystem()
  implicit val mat = ActorMaterializer()

  import system.dispatcher

  val source = Source.fromIterator(() => TwitterClient.retrieveTweets("#Akka"))

  val normalize = Flow[Status].map { t =>
    Tweet(Author(t.getUser.getName), t.getText)
  }

  val sink = Sink.foreach[Tweet](println)

  source
    .via(normalize)
    .runWith(sink)
    .onComplete(_ => system.terminate())
}
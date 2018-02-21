package com.packt.akka

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Sink, Source}
import com.packt.akka.actor.StatusPublisher
import com.packt.akka.client.TwitterStreamClient
import com.packt.akka.model.Tweet
import com.packt.akka.model.Tweet.Hashtag

import scala.concurrent.Await
import scala.concurrent.duration._

object StreamTweetsApp extends App {

  implicit val system = ActorSystem()
  implicit val mat = ActorMaterializer()

  import system.dispatcher

  val streamClient = new TwitterStreamClient(system)
  streamClient.init()

  val source = Source.actorPublisher[Tweet](StatusPublisher.props)

  val normalize = Flow[Tweet]
    .filter(_.hashtags.contains(Hashtag("#Akka")))

  val sink = Sink.foreach[Tweet](println(_))

  source.via(normalize).runWith(sink).onComplete { _ =>
    system.terminate()
    Await.ready(system.whenTerminated, 10 seconds)
  }
}
package com.packt.akka

import java.nio.file.Paths

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl._
import akka.util.ByteString

import scala.concurrent.Await
import scala.concurrent.duration._

object ReadStreamApp extends App {

  implicit val system = ActorSystem()
  implicit val mat = ActorMaterializer()
  import system.dispatcher

  // read lines from a log file
  val logFile = Paths.get("src/main/resources/log.txt")

  val source = FileIO.fromPath(logFile)

  // parse chunks of bytes into lines
  val flow = Framing
    .delimiter(ByteString(System.lineSeparator()),
      maximumFrameLength = 512,
      allowTruncation = true)
    .map(_.utf8String)

  val sink = Sink.foreach(println)

  source.via(flow).runWith(sink).onComplete { _ =>
    system.terminate()
    Await.ready(system.whenTerminated, 10 seconds)
  }
}
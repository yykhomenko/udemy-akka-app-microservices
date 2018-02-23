package com.packt.akka

import java.nio.file.Paths

import akka.actor.ActorSystem
import akka.stream.scaladsl._
import akka.stream.{ActorMaterializer, ClosedShape}
import akka.util.ByteString

import scala.util.{Failure, Success}

object WriteStreamApp extends App {

  implicit val system = ActorSystem()
  implicit val mat = ActorMaterializer()
  import system.dispatcher

  val file = Paths.get("target/prime.txt")

  // Source
  val source = Source(1 to 10000).filter(isPrime)

  // Sink
  val sink = FileIO.toPath(file)

  // file output sink
  val fileSink = Flow[Int]
    .map(i => ByteString(i.toString))
    .toMat(sink)((_, bytesWritten) => bytesWritten)

  // console output sink
  val consoleSink = Sink.foreach[Int](println)

  // send primes to both file sink and console sink using graph API
  val g = RunnableGraph.fromGraph(GraphDSL.create(fileSink, consoleSink)((file, _) => file) { implicit builder =>

    (file, console) =>
      import GraphDSL.Implicits._

      val broadcast = builder.add(Broadcast[Int](2))

      source ~> broadcast ~> file
                broadcast ~> console

      ClosedShape
  }).run()

  // ensure the output file is closed and the system shutdown upon completion
  g.onComplete {
    case Success(_) =>
      system.terminate()
    case Failure(e) =>
      println(s"Failure: ${e.getMessage}")
      system.terminate()
  }

  def isPrime(n: Int) =
    if (n <= 1) false
    else if (n == 2) true
    else !(2 until n).exists(x => n % x == 0)
}
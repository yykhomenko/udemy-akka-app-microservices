package com.packt.akka

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Sink

import scala.io.StdIn

object LowLevel extends App {

  implicit val sys = ActorSystem()
  implicit val mat = ActorMaterializer()
  implicit val dis = sys.dispatcher

  val source = Http().bind(interface = "localhost", port = 8888)

  val handler: HttpRequest => HttpResponse = {
    case HttpRequest(GET, Uri.Path("/"), _, _, _) =>
      HttpResponse(entity = HttpEntity("Hello Akka HTTP Server Side API - Low Level!"))
    case _ =>
      HttpResponse(404, entity = "Unknown resource!")
  }

  val sink = Sink.foreach[Http.IncomingConnection] { connection =>
    println("Accepted new connection from " + connection.remoteAddress)
    connection handleWithSyncHandler handler
  }

  val binding = source.to(sink).run()

  println(s"Server online at http://localhost:8888/\nPress RETURN to stop...")
  StdIn.readLine()

  binding
    .flatMap(_.unbind())
    .onComplete(_ => sys.terminate())
}